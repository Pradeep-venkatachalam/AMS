package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.ClassRoom;
import org.example.attendancemanagementsystem.Model.StudentModel;
import org.example.attendancemanagementsystem.Repository.ClassRoomRepository;
import org.example.attendancemanagementsystem.Repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository sr;

    @Autowired
    ClassRoomRepository classRoomRepo;

    public String addStudent(StudentModel studentModel) {

        Integer classroomId = studentModel.getClassroom().getClassroomId();

        ClassRoom classroom = classRoomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        studentModel.setClassroom(classroom);

        sr.save(studentModel);

        return "Student added";
    }

    public StudentModel showStudent(Integer id) {

        return sr.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<StudentModel> showaAllStudent() {

        return sr.findAll();
    }

    public String updateStudent(StudentModel student) {

        if (!sr.existsById(student.getStudentId())) {

            throw new RuntimeException("Student not found");
        }

        Integer classroomId = student.getClassroom().getClassroomId();

        ClassRoom classroom = classRoomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        student.setClassroom(classroom);

        sr.save(student);

        return "student updated";
    }

    public List<StudentModel> showaAllStudentbyadmin(StudentModel studentModel) {

        return sr.findAll();
    }

    public StudentModel showaAllStudentbyIdadmin(int id) {

        return sr.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}