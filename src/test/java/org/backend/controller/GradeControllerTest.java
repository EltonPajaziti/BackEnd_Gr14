package org.backend.controller;

import org.backend.model.Grade;
import org.backend.service.GradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GradeControllerTest {

    private GradeController gradeController;
    private GradeService gradeService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        gradeController = new GradeController();
        gradeService = Mockito.mock(GradeService.class);

        // Reflektim për injektim të gradeService
        Field gradeServiceField = GradeController.class.getDeclaredField("gradeService");
        gradeServiceField.setAccessible(true);
        gradeServiceField.set(gradeController, gradeService);

        mockMvc = MockMvcBuilders.standaloneSetup(gradeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllGrades() throws Exception {
        Grade grade1 = new Grade();
        grade1.setId(1L);

        Grade grade2 = new Grade();
        grade2.setId(2L);

        Mockito.when(gradeService.getAllGrades()).thenReturn(Arrays.asList(grade1, grade2));

        mockMvc.perform(get("/api/grades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testGetGradeById_Found() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);

        Mockito.when(gradeService.getGradeById(1L)).thenReturn(Optional.of(grade));

        mockMvc.perform(get("/api/grades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetGradeById_NotFound() throws Exception {
        Mockito.when(gradeService.getGradeById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/grades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateGrade() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);

        Mockito.when(gradeService.createGrade(any(Grade.class))).thenReturn(grade);

        String json = objectMapper.writeValueAsString(grade);

        mockMvc.perform(post("/api/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateGrade() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);

        Mockito.when(gradeService.updateGrade(eq(1L), any(Grade.class))).thenReturn(grade);

        String json = objectMapper.writeValueAsString(grade);

        mockMvc.perform(put("/api/grades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteGrade() throws Exception {
        Mockito.doNothing().when(gradeService).deleteGrade(1L);

        mockMvc.perform(delete("/api/grades/1"))
                .andExpect(status().isOk());
    }
}
