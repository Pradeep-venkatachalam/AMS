const BASE = 'http://localhost:8080';

function authHeaders() {
  const creds = sessionStorage.getItem('credentials');
  return {
    'Content-Type': 'application/json',
    ...(creds ? { 'Authorization': 'Basic ' + creds } : {})
  };
}

function showToast(msg, type = 'info') {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = type;
  t.style.display = 'block';
  setTimeout(() => t.style.display = 'none', 3500);
}

function logout() {
  sessionStorage.clear();
  location.href = 'index.html';
}

// ─── SECTION SWITCHING ──────────────────────────────────
function showSection(name) {
  document.querySelectorAll('section[id^="sec-"]').forEach(s => s.style.display = 'none');
  document.getElementById('sec-' + name).style.display = '';
  document.querySelectorAll('.sidebar-nav a').forEach(a => a.classList.remove('active'));
  event.target.closest('a').classList.add('active');
  if (name === 'dashboard') loadDashboard();
  if (name === 'students') loadStudents();
}

// ─── DASHBOARD ──────────────────────────────────────────
async function loadDashboard() {
  try {
    const res  = await fetch(`${BASE}/admin/showAllStudentbyadmin`, { headers: authHeaders() });
    const data = await res.json();
    document.getElementById('dash-students').textContent = data.length;
    renderStudentTable('dash-recent-table', data.slice(0, 5));
  } catch(e) { /* server not connected */ }
}

// ─── STUDENTS ───────────────────────────────────────────
async function createStudent() {
  const body = {
    studentname: document.getElementById('s-name').value,
    studentdept: document.getElementById('s-dept').value,
    studentpass: document.getElementById('s-pass').value,
    classroom: { classroomId: parseInt(document.getElementById('s-cid').value) }
  };
  if (!body.studentname || !body.studentdept || !body.studentpass) {
    showToast('Please fill all student fields.', 'error'); return;
  }
  try {
    const res  = await fetch(`${BASE}/admin/createStudent`, {
      method: 'POST', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}

async function updateStudent() {
  const id = document.getElementById('us-id').value;
  if (!id) { showToast('Enter a student ID.', 'error'); return; }
  const body = {
    studentname: document.getElementById('us-name').value,
    studentdept: document.getElementById('us-dept').value,
    classroom: { classroomId: parseInt(document.getElementById('us-cid').value) }
  };
  try {
    const res  = await fetch(`${BASE}/admin/updateStudent/${id}`, {
      method: 'PUT', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
    loadStudents();
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}

async function loadStudents() {
  const container = document.getElementById('student-table');
  container.innerHTML = '<div class="empty"><div class="empty-icon">⏳</div><p>Loading…</p></div>';
  try {
    const res  = await fetch(`${BASE}/admin/showAllStudentbyadmin`, { headers: authHeaders() });
    const data = await res.json();
    document.getElementById('dash-students').textContent = data.length;
    renderStudentTable('student-table', data);
  } catch(e) {
    container.innerHTML = '<div class="empty"><div class="empty-icon">⚠️</div><p>Failed to load. Is the backend running?</p></div>';
  }
}

function renderStudentTable(containerId, data) {
  const container = document.getElementById(containerId);
  if (!data || data.length === 0) {
    container.innerHTML = '<div class="empty"><div class="empty-icon">🎓</div><p>No students found.</p></div>';
    return;
  }
  let html = `<table>
    <thead><tr>
      <th>ID</th><th>Name</th><th>Department</th><th>Classroom</th>
    </tr></thead><tbody>`;
  data.forEach(s => {
    html += `<tr>
      <td><span class="chip chip-green">#${s.studentId}</span></td>
      <td>${s.studentname}</td>
      <td>${s.studentdept}</td>
      <td>${s.classroom ? s.classroom.className || s.classroom.classroomId : '—'}</td>
    </tr>`;
  });
  html += '</tbody></table>';
  container.innerHTML = html;
}

// ─── TEACHERS ───────────────────────────────────────────
async function createTeacher() {
  const body = {
    teachername: document.getElementById('t-name').value,
    teacherdept: document.getElementById('t-dept').value,
    teacherpass: document.getElementById('t-pass').value
  };
  if (!body.teachername || !body.teacherdept || !body.teacherpass) {
    showToast('Please fill all teacher fields.', 'error'); return;
  }
  try {
    const res  = await fetch(`${BASE}/admin/createTeacher`, {
      method: 'POST', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}

async function viewTeacher() {
  const id = document.getElementById('t-view-id').value;
  if (!id) { showToast('Enter a teacher ID.', 'error'); return; }
  try {
    const res  = await fetch(`${BASE}/admin/teacher/${id}`, { headers: authHeaders() });
    const data = await res.json();
    document.getElementById('teacher-result').innerHTML = `
      <div class="card" style="margin:0;background:var(--surface2)">
        <strong>${data.teachername}</strong>
        <p style="color:var(--text-muted);font-size:13px;margin-top:4px">Dept: ${data.teacherdept}</p>
      </div>`;
  } catch(e) { showToast('Teacher not found.', 'error'); }
}

// ─── CLASSROOMS ─────────────────────────────────────────
async function createClassroom() {
  const name = document.getElementById('c-name').value;
  if (!name) { showToast('Enter a classroom name.', 'error'); return; }
  try {
    const res  = await fetch(`${BASE}/admin/createClassroom`, {
      method: 'POST', headers: authHeaders(),
      body: JSON.stringify({ className: name })
    });
    const data = await res.text();
    showToast(data, 'success');
    document.getElementById('c-name').value = '';
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}

// ─── ATTENDANCE ──────────────────────────────────────────
async function markAttendance() {
  // BUG FIX: original used element IDs that clashed with button IDs
  const sid     = document.getElementById('att-sid').value;
  const present = document.getElementById('att-present').value;
  const total   = document.getElementById('att-total').value;

  if (!sid || !present || !total) { showToast('Fill all attendance fields.', 'error'); return; }

  const body = {
    studentModel: { studentId: parseInt(sid) },
    presenthour: parseInt(present),
    totalhour:   parseInt(total)
  };
  try {
    const res  = await fetch(`${BASE}/admin/student/attend`, {
      method: 'POST', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}

async function viewAttendance() {
  const id = document.getElementById('att-view-id').value;
  if (!id) { showToast('Enter an attendance record ID.', 'error'); return; }
  try {
    const res  = await fetch(`${BASE}/admin/attendance/${id}`, { headers: authHeaders() });
    const a    = await res.json();
    const pct  = a.attendancePercentage ? a.attendancePercentage.toFixed(1) : 0;
    const cls  = pct >= 75 ? 'good' : pct >= 50 ? 'average' : 'poor';
    document.getElementById('att-result').innerHTML = `
      <div style="margin-top:0">
        <p>Total: <strong>${a.totalhour}h</strong> | Present: <strong>${a.presenthour}h</strong> | Absent: <strong>${a.absenthour}h</strong></p>
        <p style="margin-top:8px;font-size:13px;color:var(--text-muted)">${pct}% attendance</p>
        <div class="progress-bar" style="margin-top:6px">
          <div class="progress-fill ${cls}" style="width:${pct}%"></div>
        </div>
      </div>`;
  } catch(e) { showToast('Record not found.', 'error'); }
}

// ─── INIT ────────────────────────────────────────────────
window.addEventListener('DOMContentLoaded', loadDashboard);
