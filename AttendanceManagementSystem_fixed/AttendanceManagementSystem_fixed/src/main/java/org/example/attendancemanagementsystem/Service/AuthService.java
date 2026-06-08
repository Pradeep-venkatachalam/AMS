package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.User;
import org.example.attendancemanagementsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * BUG FIX: previously returned "Login Success" for everyone which prevented
     * role-based redirects in the frontend. Now returns role-specific messages
     * so the frontend can redirect to the correct dashboard.
     */
    public String login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "User not found";
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            // Return role so frontend can redirect
            return "Login Success:" + user.getRole().name();
        } else {
            return "Invalid Password";
        }
    }
}
