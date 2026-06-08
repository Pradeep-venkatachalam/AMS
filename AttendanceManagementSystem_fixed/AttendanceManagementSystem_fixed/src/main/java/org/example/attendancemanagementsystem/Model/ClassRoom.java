package org.example.attendancemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classroomId;

    @NotBlank(message = "classroomName is required")
    private String className;

    // MANY CLASSROOMS → ONE TEACHER
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherModel teacherModel;

    // ONE CLASSROOM → MANY STUDENTS
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudentModel> studentModel = new ArrayList<>();

    public ClassRoom(String className, TeacherModel teacherModel) {
        this.className = className;
        this.teacherModel = teacherModel;
    }
}