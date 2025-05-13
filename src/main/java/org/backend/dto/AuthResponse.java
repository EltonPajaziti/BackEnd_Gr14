package org.backend.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private Long tenantId;

    public AuthResponse(String token, Long userId, Long tenantId) {
        this.token = token;
        this.userId = userId;
        this.tenantId = tenantId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTenantId() {
        return tenantId;
    }
}
