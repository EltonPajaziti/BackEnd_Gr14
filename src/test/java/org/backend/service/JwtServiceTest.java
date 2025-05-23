package org.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGenerateTokenAndExtractClaims() {
        String email = "test@example.com";
        String role = "ADMIN";

        String token = jwtService.generateToken(email, role);
        assertNotNull(token);

        String extractedEmail = jwtService.extractEmail(token);
        String extractedRole = jwtService.extractRole(token);

        assertEquals(email, extractedEmail);
        assertEquals(role, extractedRole);
    }

    @Test
    void testExtractEmail_invalidToken() {
        String invalidToken = "invalid.token.value";

        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(invalidToken);
        });
    }

    @Test
    void testExtractRole_invalidToken() {
        String invalidToken = "invalid.token.value";

        assertThrows(Exception.class, () -> {
            jwtService.extractRole(invalidToken);
        });
    }
}
