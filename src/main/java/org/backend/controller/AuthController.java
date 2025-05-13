package org.backend.controller;

import org.backend.dto.AuthRequest;
import org.backend.dto.AuthResponse;
import org.backend.model.Users;
import org.backend.repository.UsersRepository;
import org.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Users user = usersRepository.findByEmail(authRequest.getEmail());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getTenantID() != null ? user.getTenantID().getId() : null
        );

        return ResponseEntity.ok(response);
    }
}
