package org.example.attendancemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @NotBlank(message = "Studentname is required")
    private String studentname;

    @NotBlank(message = "Studentdept is required")
    private String studentdept;

    @NotBlank(message = "Studentpass is required")
    private String studentpass;

    // MANY STUDENTS → ONE CLASSROOM
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classroom;

    // ONE STUDENT → MANY ATTENDANCE RECORDS
    @OneToMany(mappedBy = "studentModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AttendanceModel> attendanceModel = new ArrayList<>();
}