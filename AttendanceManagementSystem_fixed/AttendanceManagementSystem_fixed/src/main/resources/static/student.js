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

async function viewAttendance() {
  const id = document.getElementById('stud-id').value;
  const container = document.getElementById('attendance-result');
  if (!id) { showToast('Enter a student ID.', 'error'); return; }

  container.innerHTML = '<div class="empty"><div class="empty-icon">⏳</div><p>Loading…</p></div>';

  try {
    const res  = await fetch(`${BASE}/student/showstudent/${id}`, { headers: authHeaders() });
    const data = await res.json();

    if (!data || !data.attendanceModel) {
      container.innerHTML = '<div class="empty"><div class="empty-icon">📭</div><p>No records found.</p></div>';
      return;
    }

    const records = data.attendanceModel;
    let totalPresent = 0, totalHours = 0;
    records.forEach(a => { totalPresent += a.presenthour; totalHours += a.totalhour; });
    const overallPct = totalHours > 0 ? ((totalPresent / totalHours) * 100).toFixed(1) : 0;
    const cls = overallPct >= 75 ? 'good' : overallPct >= 50 ? 'average' : 'poor';
    const chipClass = overallPct >= 75 ? 'chip-green' : overallPct >= 50 ? 'chip-yellow' : 'chip-red';

    let html = `
      <div class="card" style="margin-top:0">
        <h3>📊 Summary — ${data.studentname}
          <span class="chip ${chipClass}">${overallPct}%</span>
        </h3>
        <div class="grid-3" style="margin-bottom:20px">
          <div class="stat-card">
            <div class="stat-value">${totalHours}</div>
            <div class="stat-label">Total Hours</div>
          </div>
          <div class="stat-card">
            <div class="stat-value" style="color:var(--success)">${totalPresent}</div>
            <div class="stat-label">Present Hours</div>
          </div>
          <div class="stat-card">
            <div class="stat-value" style="color:var(--danger)">${totalHours - totalPresent}</div>
            <div class="stat-label">Absent Hours</div>
          </div>
        </div>
        <div class="progress-bar">
          <div class="progress-fill ${cls}" style="width:${overallPct}%"></div>
        </div>
        <p style="font-size:12px;color:var(--text-muted);margin-top:6px">Overall: ${overallPct}%</p>
      </div>`;

    if (records.length > 0) {
      html += `<div class="card"><h3>Attendance Records</h3><div class="table-wrap">
        <table><thead><tr><th>Record ID</th><th>Present</th><th>Total</th><th>%</th><th>Status</th></tr></thead><tbody>`;
      records.forEach(a => {
        const pct = a.attendancePercentage ? a.attendancePercentage.toFixed(1) : 0;
        const sc  = pct >= 75 ? 'chip-green' : pct >= 50 ? 'chip-yellow' : 'chip-red';
        html += `<tr>
          <td>#${a.attendanceId}</td>
          <td>${a.presenthour}h</td>
          <td>${a.totalhour}h</td>
          <td>${pct}%</td>
          <td><span class="chip ${sc}">${pct >= 75 ? 'Good' : pct >= 50 ? 'Average' : 'Low'}</span></td>
        </tr>`;
      });
      html += '</tbody></table></div></div>';
    }

    container.innerHTML = html;
  } catch(e) {
    container.innerHTML = '<div class="empty"><div class="empty-icon">⚠️</div><p>Student not found or server unavailable.</p></div>';
  }
}

async function viewProfile() {
  const id = document.getElementById('profile-id').value;
  const container = document.getElementById('profile-result');
  if (!id) return;

  try {
    const res  = await fetch(`${BASE}/student/showstudent/${id}`, { headers: authHeaders() });
    const data = await res.json();
    container.innerHTML = `
      <div style="background:var(--surface2);border-radius:8px;padding:16px">
        <p><strong>Name:</strong> ${data.studentname}</p>
        <p style="margin-top:8px"><strong>Department:</strong> ${data.studentdept}</p>
        <p style="margin-top:8px"><strong>Classroom:</strong> ${data.classroom ? data.classroom.className || data.classroom.classroomId : '—'}</p>
      </div>`;
  } catch(e) { showToast('Student not found.', 'error'); }
}
