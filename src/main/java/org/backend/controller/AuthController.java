package org.backend.controller;

import org.backend.dto.AuthRequest;
import org.backend.model.Users;
import org.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Users user = usersRepository.findByEmail(authRequest.getEmail());
        if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return "Login successful for user: " + user.getEmail();
        } else {
            return "Invalid credentials";
        }
    }
}