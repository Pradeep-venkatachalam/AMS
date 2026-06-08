package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.User;
import org.example.attendancemanagementsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String  register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

         userRepository.save(user);
        return "success encrpt and register";

    }
}
