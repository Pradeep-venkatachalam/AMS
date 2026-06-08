package org.example.attendancemanagementsystem.Controller;

import org.example.attendancemanagementsystem.Model.*;
import org.example.attendancemanagementsystem.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    // ---------------- STUDENT ----------------

    @Autowired
    StudentService ss;

    @PostMapping("/createStudent")
    public String addStudent(@RequestBody StudentModel studentModel) {
        return ss.addStudent(studentModel);
    }

    @PutMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable int id,
                                @RequestBody StudentModel student) {

        student.setStudentId(id);
        return ss.updateStudent(student);
    }

    @GetMapping("/showAllStudentbyadmin")
    public List<StudentModel> showStudent(StudentModel studentModel) {
        return ss.showaAllStudentbyadmin(studentModel);
    }

    @GetMapping("/ShowStudentbyIdadmin/{id}")
    public StudentModel showStudentByIdadmin(@PathVariable int id) {
        return ss.showaAllStudentbyIdadmin(id);
    }

    // ---------------- ATTENDANCE ----------------

    @Autowired
    AttendanceService as;

    @PostMapping("/student/attend")
    public String stuattend(@RequestBody AttendanceModel attendanceModel) {
        return as.stuattend(attendanceModel);
    }

    @PostMapping("/teacher/attend")
    public String teacherattend(@RequestBody AttendanceModel attendanceModel) {
        return as.teacherattend(attendanceModel);
    }

    // SHOW ATTENDANCE WITH STUDENT + CLASS DETAILS
    @GetMapping("/attendance/{id}")
    public AttendanceModel getAttendance(@PathVariable int id) {
        return as.getAttendanceById(id);
    }

    @PutMapping("/attendance/{id}")
    public String updateAttendance(@PathVariable int id,
                                   @RequestBody AttendanceModel attendanceModel) {

        attendanceModel.setAttendanceId(id);

        return as.updateAttendance(attendanceModel);
    }

    // ---------------- TEACHER ----------------

    @Autowired
    TeacherService ts;

    @PostMapping("/createTeacher")
    public String addteacher(@RequestBody TeacherModel teacherModel) {
        return ts.addteacher(teacherModel);
    }

    @PutMapping("/updateTeacher/{id}")
    public String updateteacher(@PathVariable int id,
                                @RequestBody TeacherModel teacher) {

        teacher.setTeacherId(id);
        return ts.updateteacher(teacher);
    }

    @GetMapping("/teacher/{id}")
    public TeacherModel ShowTeacherById(@PathVariable int id) {
        return ts.ShowTeacherById(id);
    }

    // ---------------- ADMIN ----------------

    @Autowired
    AdminService ass;

    @PostMapping("/adminCreate")
    public String addAdmin(@RequestBody AdminModel adminModel) {
        return ass.addAdmin(adminModel);
    }

    @GetMapping("/showAdmin")
    public List<AdminModel> showAdmin(AdminModel adminModel) {
        return ass.showAdmin(adminModel);
    }

    @GetMapping("/{id}")
    public AdminModel showAdminbyid(@PathVariable int id) {
        return ass.showAdminbyid(id);
    }

    @PutMapping("/updateadmin")
    public String updateAdmin(@RequestBody AdminModel adminModel) {
        return ass.updateAdmin(adminModel);
    }

    // ------------ CLASS ROOM -----------

    @Autowired
    ClassRoomService cs;

    @PostMapping("/createClassroom")
    public String createClassroom(@RequestBody ClassRoom classroom) {

        return cs.createClassroom(classroom);
    }


    //-------------Login-------------
    @GetMapping("/dashboard")
    public String dashboard() {

        return "Dashboard";
    }


}





