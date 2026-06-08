package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.TeacherModel;
import org.example.attendancemanagementsystem.Repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository tr;

    public String addteacher(TeacherModel teacherModel) {

        tr.save(teacherModel);

        return "Teacher added";
    }

    public String updateteacher(TeacherModel teacher) {

        tr.save(teacher);

        return "Teacher updated";
    }

    public TeacherModel ShowTeacherById(int id) {

        return tr.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }
}