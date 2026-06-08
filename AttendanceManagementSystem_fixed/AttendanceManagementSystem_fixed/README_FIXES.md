# AttendX — Fixed & Enhanced

## Bug Fixes Applied

### Backend Fixes

| File | Bug | Fix |
|------|-----|-----|
| `SecurityConfig.java` | Only `/login` and `/admin/` were permitted — all static HTML/JS/CSS and `/auth/**` were blocked with 401 | Added permit for `/*.html`, `/*.css`, `/*.js`, `/auth/login`, `/auth/register` |
| `AdminService.java` | `updateAdmin()` had inverted logic — saved when ID doesn't exist, returned error when it does | Fixed condition to check existence before updating |
| `AuthService.java` | `login()` returned `"Login Success"` for all roles — frontend could never redirect to the correct dashboard | Now returns `"Login Success:ROLE"` (e.g. `"Login Success:ADMIN"`) |
| `pom.xml` | `<source>25</source><target>25</target>` (Java 25 doesn't exist) conflicted with `<release>21</release>` | Removed invalid source/target tags |

### Frontend Fixes

| File | Bug | Fix |
|------|-----|-----|
| `admin.js` | `present.value` / `total.value` referenced button elements by ID, not the input fields | Renamed input IDs to `att-present` / `att-total` |
| `login.js` | Redirect checked for `"Admin"` / `"Teacher"` / `"Student"` in response but backend returned `"Login Success"` | Updated to parse `"Login Success:ROLE"` format |
| All HTML pages | Bare HTML with no layout, accessibility, or UX | Full redesign with sidebar, cards, forms, tables, progress bars |

## Setup

1. Set up MySQL and create the `attendSystem` database:
   ```sql
   CREATE DATABASE attendSystem;
   ```

2. Update `src/main/resources/application.properties` with your DB credentials.

3. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Open `http://localhost:8080/index.html`

## First Steps

1. Register a user via the Login page (or `POST /auth/register`)
2. Log in — you'll be redirected to your role dashboard
3. Admin: create classrooms first, then teachers and students

## API Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login → returns `Login Success:ROLE` |
| POST | `/admin/createStudent` | Create student |
| PUT | `/admin/updateStudent/{id}` | Update student |
| GET | `/admin/showAllStudentbyadmin` | List all students |
| POST | `/admin/createTeacher` | Create teacher |
| GET | `/admin/teacher/{id}` | Get teacher by ID |
| POST | `/admin/createClassroom` | Create classroom |
| POST | `/admin/student/attend` | Mark student attendance |
| GET | `/admin/attendance/{id}` | Get attendance record |
| GET | `/student/showstudent/{id}` | Student view with attendance |
| GET | `/teacher/ShowallStudent` | Teacher view all students |
