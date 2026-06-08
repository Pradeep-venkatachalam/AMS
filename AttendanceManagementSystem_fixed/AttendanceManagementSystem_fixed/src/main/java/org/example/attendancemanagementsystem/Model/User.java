package org.example.attendancemanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendancemanagementsystem.Model.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    // IMPORTANT FIX
    @Enumerated(EnumType.STRING)
    private Role role;

    public  User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}