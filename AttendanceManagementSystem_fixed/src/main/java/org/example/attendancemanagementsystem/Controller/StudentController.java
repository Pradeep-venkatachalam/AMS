package org.example.attendancemanagementsystem.Controller;

import org.example.attendancemanagementsystem.Model.StudentModel;
import org.example.attendancemanagementsystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    StudentService ss;

    @GetMapping("/showstudent/{id}")
    public StudentModel showStudent(@PathVariable Integer id) {

        return ss.showStudent(id);
    }
}