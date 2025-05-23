//package org.backend.middleware;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.backend.dto.AuthRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//import static org.mockito.Mockito.*;
//
//public class AuthValidationMiddlewareTest {
//
//    private AuthValidationMiddleware middleware;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        middleware = new AuthValidationMiddleware();
//        objectMapper = new ObjectMapper();
//    }
//
//    private HttpServletRequest createMockRequest(String uri, String method, AuthRequest authRequest) throws IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getRequestURI()).thenReturn(uri);
//        when(request.getMethod()).thenReturn(method);
//
//        String body = objectMapper.writeValueAsString(authRequest);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
//
//        ServletInputStream servletInputStream = new ServletInputStream() {
//            @Override
//            public int read() throws IOException {
//                return inputStream.read();
//            }
//
//            @Override public boolean isFinished() { return inputStream.available() == 0; }
//            @Override public boolean isReady() { return true; }
//            @Override public void setReadListener(jakarta.servlet.ReadListener readListener) {}
//        };
//
//        when(request.getInputStream()).thenReturn(servletInputStream);
//        when(request.getReader()).thenReturn(new java.io.BufferedReader(new java.io.InputStreamReader(inputStream)));
//
//        return request;
//    }
//
//    @Test
//    void shouldAllowValidRequest() throws Exception {
//        AuthRequest validRequest = new AuthRequest();
//        validRequest.setEmail("test@example.com");
//        validRequest.setPassword("validPass123");
//
//        HttpServletRequest request = createMockRequest("/api/auth/login", "POST", validRequest);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        FilterChain chain = mock(FilterChain.class);
//
//        middleware.doFilterInternal(request, response, chain);
//
//        verify(chain, times(1)).doFilter(any(), any());
//        verify(response, never()).sendError(anyInt(), anyString());
//    }
//
//    @Test
//    void shouldRejectInvalidEmail() throws Exception {
//        AuthRequest invalidRequest = new AuthRequest();
//        invalidRequest.setEmail("invalid-email");
//        invalidRequest.setPassword("validPass123");
//
//        HttpServletRequest request = createMockRequest("/api/auth/login", "POST", invalidRequest);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        FilterChain chain = mock(FilterChain.class);
//
//        middleware.doFilterInternal(request, response, chain);
//
//        verify(response).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), contains("Email-i është i pavlefshëm"));
//        verify(chain, never()).doFilter(any(), any());
//    }
//
//    @Test
//    void shouldRejectShortPassword() throws Exception {
//        AuthRequest invalidRequest = new AuthRequest();
//        invalidRequest.setEmail("test@example.com");
//        invalidRequest.setPassword("short");
//
//        HttpServletRequest request = createMockRequest("/api/auth/login", "POST", invalidRequest);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        FilterChain chain = mock(FilterChain.class);
//
//        middleware.doFilterInternal(request, response, chain);
//
//        verify(response).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), contains("Fjalëkalimi duhet"));
//        verify(chain, never()).doFilter(any(), any());
//    }
//}
