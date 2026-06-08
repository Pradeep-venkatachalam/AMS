const BASE = 'http://localhost:8080';

function showToast(msg, type='info') {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = type;
  t.style.display = 'block';
  setTimeout(() => t.style.display = 'none', 3500);
}

async function login() {
  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value;
  const role     = document.getElementById('role').value;
  const msg      = document.getElementById('msg');

  msg.textContent = '';
  if (!username || !password) { msg.textContent = 'Please fill in all fields.'; return; }

  try {
    const res  = await fetch(`${BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });
    const data = await res.text();

    // BUG FIX: backend now returns "Login Success:ROLE"
    if (data.startsWith('Login Success')) {
      const returnedRole = data.split(':')[1] || role;
      sessionStorage.setItem('role', returnedRole);
      sessionStorage.setItem('username', username);
      sessionStorage.setItem('credentials', btoa(username + ':' + password));

      showToast('Welcome back, ' + username + '!', 'success');
      setTimeout(() => {
        if (returnedRole === 'ADMIN')   location.href = 'admin.html';
        else if (returnedRole === 'TEACHER') location.href = 'teacher.html';
        else location.href = 'student.html';
      }, 800);
    } else {
      msg.textContent = data;
    }
  } catch(e) {
    msg.textContent = 'Cannot connect to server. Make sure the backend is running.';
  }
}

async function register() {
  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value;
  const role     = document.getElementById('role').value;

  if (!username || !password) {
    document.getElementById('msg').textContent = 'Enter username and password to register.';
    return;
  }

  const res  = await fetch(`${BASE}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password, role })
  });
  const data = await res.text();
  showToast(data, 'success');
}

// Allow Enter key to submit
document.addEventListener('keydown', e => { if (e.key === 'Enter') login(); });
