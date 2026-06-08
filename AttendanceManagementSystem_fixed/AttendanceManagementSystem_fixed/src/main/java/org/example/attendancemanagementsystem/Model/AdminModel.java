package org.example.attendancemanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminID;

    @NotBlank(message = "adminName is required")
    private String adminName;

    @NotBlank(message = "adminPassword is required")
    private String adminPassword;

    @NotBlank(message = "role is required")
    private String role;
}