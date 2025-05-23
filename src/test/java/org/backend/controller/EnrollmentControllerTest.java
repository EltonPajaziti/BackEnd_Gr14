package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.dto.EnrollmentRequestDTO;
import org.backend.dto.RegisteredCourseDTO;
import org.backend.model.Enrollment;
import org.backend.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EnrollmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController).build();
    }

    @Test
    public void testGetAllEnrollments() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.getAllEnrollments()).thenReturn(List.of(enrollment));

        mockMvc.perform(get("/api/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(enrollmentService, times(1)).getAllEnrollments();
    }

    @Test
    public void testGetEnrollmentByIdFound() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.getEnrollmentById(1L)).thenReturn(Optional.of(enrollment));

        mockMvc.perform(get("/api/enrollments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    public void testCreateEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.createEnrollment(any(Enrollment.class))).thenReturn(enrollment);

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(enrollmentService, times(1)).createEnrollment(any(Enrollment.class));
    }

    @Test
    public void testEnrollStudent() throws Exception {
        EnrollmentRequestDTO dto = new EnrollmentRequestDTO();
        // Vendos vlera te nevojshme ne dto sipas modelit tuaj

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.enrollStudent(any(EnrollmentRequestDTO.class))).thenReturn(enrollment);

        mockMvc.perform(post("/api/enrollments/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(enrollmentService, times(1)).enrollStudent(any(EnrollmentRequestDTO.class));
    }

    @Test
    public void testDeleteEnrollment() throws Exception {
        doNothing().when(enrollmentService).deleteEnrollment(1L);

        mockMvc.perform(delete("/api/enrollments/1"))
                .andExpect(status().isOk());

        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }


}
