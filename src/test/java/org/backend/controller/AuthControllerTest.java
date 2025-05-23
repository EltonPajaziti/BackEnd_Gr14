package org.backend.controller;

import org.backend.dto.AuthRequest;
import org.backend.model.UserRole;
import org.backend.model.Users;
import org.backend.repository.UsersRepository;
import org.backend.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {

        Users user = new Users();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        UserRole role = new UserRole();
        role.setName("STUDENT");
        user.setRole(role);


        when(usersRepository.findByEmail("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("user@example.com", "STUDENT")).thenReturn("jwt-token");


        String json = """
                {
                    "email": "user@example.com",
                    "password": "password123"
                }
                """;

        // Perform request and assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.role").value("STUDENT"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(usersRepository.findByEmail("wrong@example.com")).thenReturn(null);

        String json = """
                {
                    "email": "wrong@example.com",
                    "password": "wrongpass"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }
}
