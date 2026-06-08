package org.example.attendancemanagementsystem.Controller;

import org.example.attendancemanagementsystem.Model.StudentModel;
import org.example.attendancemanagementsystem.Model.TeacherModel;
import org.example.attendancemanagementsystem.Service.StudentService;
import org.example.attendancemanagementsystem.Service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin("*")
public class TeacherController {

    @Autowired
    TeacherService ts;

    @GetMapping("/{id}")
    public TeacherModel ShowTeacherById(@PathVariable int id) {

        return ts.ShowTeacherById(id);
    }

    @Autowired
    StudentService ss;

    @GetMapping("/ShowallStudent")
    public List<StudentModel> ShowAllStudent() {

        return ss.showaAllStudent();
    }

    @PostMapping("/createstudent")
    public String addStudent(@RequestBody StudentModel studentModel) {

        return ss.addStudent(studentModel);
    }
}