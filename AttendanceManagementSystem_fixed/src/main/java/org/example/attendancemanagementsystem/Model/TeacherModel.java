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
public class TeacherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherId;

    @NotBlank(message = "teachername is required")
    private String teachername;

    @NotBlank(message = "teacherdept is required")
    private String teacherdept;

    @NotBlank(message = "teacherpass is required")
    private String teacherpass;

    // ONE TEACHER → MANY CLASSROOMS
    @OneToMany(mappedBy = "teacherModel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClassRoom> classRooms = new ArrayList<>();

    public TeacherModel(String teachername, String teacherdept, String teacherpass) {
        this.teachername = teachername;
        this.teacherdept = teacherdept;
        this.teacherpass = teacherpass;
    }
}