package org.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Çelësi për nënshkrimin e tokenit (mund të ruhet në .properties në projekte reale)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Gjeneron token duke përfshirë edhe rolin
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // roli vendoset si claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 orë
                .signWith(key)
                .compact();
    }

    // Ekstrakton emailin nga tokeni
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Ekstrakton rolin nga tokeni
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Ekstrakton të gjitha claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
