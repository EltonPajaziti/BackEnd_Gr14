package org.backend.controller;

import org.backend.model.GradeConfirmation;
import org.backend.service.GradeConfirmationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GradeConfirmationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GradeConfirmationService gradeConfirmationService;

    @InjectMocks
    private GradeConfirmationController gradeConfirmationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gradeConfirmationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllGradeConfirmations() throws Exception {
        GradeConfirmation gc1 = new GradeConfirmation();
        gc1.setId(1L);
        GradeConfirmation gc2 = new GradeConfirmation();
        gc2.setId(2L);

        when(gradeConfirmationService.getAllGradeConfirmations()).thenReturn(Arrays.asList(gc1, gc2));

        mockMvc.perform(get("/api/gradeconfirmations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(gradeConfirmationService, times(1)).getAllGradeConfirmations();
    }

    @Test
    public void testGetGradeConfirmationById_Found() throws Exception {
        GradeConfirmation gc = new GradeConfirmation();
        gc.setId(1L);

        when(gradeConfirmationService.getGradeConfirmationById(1L)).thenReturn(Optional.of(gc));

        mockMvc.perform(get("/api/gradeconfirmations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(gradeConfirmationService, times(1)).getGradeConfirmationById(1L);
    }

    @Test
    public void testGetGradeConfirmationById_NotFound() throws Exception {
        when(gradeConfirmationService.getGradeConfirmationById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/gradeconfirmations/999"))
                .andExpect(status().isNotFound());

        verify(gradeConfirmationService, times(1)).getGradeConfirmationById(999L);
    }

    @Test
    public void testCreateGradeConfirmation() throws Exception {
        GradeConfirmation gc = new GradeConfirmation();
        gc.setId(1L);

        when(gradeConfirmationService.createGradeConfirmation(any(GradeConfirmation.class))).thenReturn(gc);

        String json = objectMapper.writeValueAsString(gc);

        mockMvc.perform(post("/api/gradeconfirmations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(gradeConfirmationService, times(1)).createGradeConfirmation(any(GradeConfirmation.class));
    }

    @Test
    public void testDeleteGradeConfirmation() throws Exception {
        doNothing().when(gradeConfirmationService).deleteGradeConfirmation(1L);

        mockMvc.perform(delete("/api/gradeconfirmations/1"))
                .andExpect(status().isOk());

        verify(gradeConfirmationService, times(1)).deleteGradeConfirmation(1L);
    }

    @Test
    public void testGetByExpirationDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 5, 23);
        GradeConfirmation gc = new GradeConfirmation();
        gc.setId(1L);

        when(gradeConfirmationService.findByExpiresAt(date)).thenReturn(gc);

        mockMvc.perform(get("/api/gradeconfirmations/expires/" + date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(gradeConfirmationService, times(1)).findByExpiresAt(date);
    }
}
