package org.backend.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ReadListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.backend.dto.AuthRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class AuthValidationMiddleware extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.contains("/api/auth/login") && "POST".equalsIgnoreCase(request.getMethod())) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                StringBuilder requestBody = new StringBuilder();
                try (BufferedReader reader = request.getReader()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }
                }
                AuthRequest authRequest = objectMapper.readValue(requestBody.toString(), AuthRequest.class);


                String email = authRequest.getEmail();
                String password = authRequest.getPassword();


                if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email-i është i pavlefshëm");
                    return;
                }


                if (password == null || password.length() < 8) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Fjalëkalimi duhet të ketë të paktën 8 karaktere");
                    return;
                }


                authRequest.setEmail(email.toLowerCase());
                authRequest.setPassword(password.trim());


                CustomRequestWrapper wrappedRequest = new CustomRequestWrapper(request, objectMapper, authRequest);
                chain.doFilter(wrappedRequest, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format i pavlefshëm i kërkesës");
                return;
            }
        } else {
            chain.doFilter(request, response);
        }
    }


    private static class CustomRequestWrapper extends HttpServletRequestWrapper {
        private final String modifiedBody;

        public CustomRequestWrapper(HttpServletRequest request, ObjectMapper objectMapper, AuthRequest authRequest) throws IOException {
            super(request);
            this.modifiedBody = objectMapper.writeValueAsString(authRequest);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(modifiedBody.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                private boolean finished = false;

                @Override
                public boolean isFinished() {
                    return finished && byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    int data = byteArrayInputStream.read();
                    if (data == -1) {
                        finished = true;
                    }
                    return data;
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(modifiedBody.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        }
    }
}