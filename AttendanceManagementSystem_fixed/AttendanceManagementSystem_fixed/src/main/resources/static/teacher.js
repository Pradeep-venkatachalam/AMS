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

function logout() { sessionStorage.clear(); location.href = 'index.html'; }

function showSection(name) {
  document.querySelectorAll('section[id^="sec-"]').forEach(s => s.style.display = 'none');
  document.getElementById('sec-' + name).style.display = '';
  document.querySelectorAll('.sidebar-nav a').forEach(a => a.classList.remove('active'));
  event.target.closest('a').classList.add('active');
}

async function loadStudents() {
  const container = document.getElementById('student-list');
  container.innerHTML = '<div class="empty"><div class="empty-icon">⏳</div><p>Loading…</p></div>';
  try {
    const res  = await fetch(`${BASE}/teacher/ShowallStudent`, { headers: authHeaders() });
    const data = await res.json();
    if (!data.length) {
      container.innerHTML = '<div class="empty"><div class="empty-icon">📭</div><p>No students found.</p></div>';
      return;
    }
    let html = `<table><thead><tr><th>ID</th><th>Name</th><th>Department</th></tr></thead><tbody>`;
    data.forEach(s => {
      html += `<tr>
        <td><span class="chip chip-green">#${s.studentId}</span></td>
        <td>${s.studentname}</td>
        <td>${s.studentdept}</td>
      </tr>`;
    });
    html += '</tbody></table>';
    container.innerHTML = html;
  } catch(e) {
    container.innerHTML = '<div class="empty"><div class="empty-icon">⚠️</div><p>Failed to load students.</p></div>';
  }
}

async function markAttend(presenthour) {
  // BUG FIX: original used element ID 'sid' but it clashed with button IDs named 'present'/'absent'
  const sid = document.getElementById('sid').value;
  const msg = document.getElementById('mark-msg');
  if (!sid) { msg.textContent = '⚠ Enter a student ID first.'; return; }

  const body = {
    studentModel: { studentId: parseInt(sid) },
    presenthour,
    totalhour: 1
  };
  try {
    const res  = await fetch(`${BASE}/admin/student/attend`, {
      method: 'POST', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
    msg.textContent = `✓ ${presenthour === 1 ? 'Marked Present' : 'Marked Absent'} for student #${sid}`;
  } catch(e) {
    showToast('Error: ' + e.message, 'error');
  }
}

function present() { markAttend(1); }
function absent()  { markAttend(0); }

async function addStudent() {
  const body = {
    studentname: document.getElementById('ns-name').value,
    studentdept: document.getElementById('ns-dept').value,
    studentpass: document.getElementById('ns-pass').value,
    classroom:   { classroomId: parseInt(document.getElementById('ns-cid').value) }
  };
  try {
    const res  = await fetch(`${BASE}/teacher/createstudent`, {
      method: 'POST', headers: authHeaders(), body: JSON.stringify(body)
    });
    const data = await res.text();
    showToast(data, 'success');
  } catch(e) { showToast('Error: ' + e.message, 'error'); }
}
