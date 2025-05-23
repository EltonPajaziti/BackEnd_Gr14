package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.backend.model.Exam;
import org.backend.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // për LocalDate
    }

    @Test
    public void testGetAll() throws Exception {
        Exam exam1 = new Exam(); exam1.setId(1L);
        Exam exam2 = new Exam(); exam2.setId(2L);

        when(examService.getAll()).thenReturn(Arrays.asList(exam1, exam2));

        mockMvc.perform(get("/api/exams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(examService, times(1)).getAll();
    }

    @Test
    public void testGetByIdFound() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        when(examService.getById(1L)).thenReturn(Optional.of(exam));

        mockMvc.perform(get("/api/exams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examService, times(1)).getById(1L);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(examService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/exams/99"))
                .andExpect(status().isNotFound());

        verify(examService, times(1)).getById(99L);
    }

    @Test
    public void testCreateInvalid() {
        Exam exam = new Exam();

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<Exam> response = examController.create(exam, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(examService, never()).create(any());
    }

    @Test
    public void testCreateValid() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setExamDate(LocalDate.now());

        when(examService.create(any(Exam.class))).thenReturn(exam);

        mockMvc.perform(post("/api/exams")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(exam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examService, times(1)).create(any(Exam.class));
    }

    @Test
    public void testUpdateValid() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setExamDate(LocalDate.now());

        when(examService.update(eq(1L), any(Exam.class))).thenReturn(exam);

        mockMvc.perform(put("/api/exams/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(exam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examService, times(1)).update(eq(1L), any(Exam.class));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Exam exam = new Exam();
        exam.setExamDate(LocalDate.now());

        when(examService.update(eq(99L), any(Exam.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/exams/99")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(exam)))
                .andExpect(status().isNotFound());

        verify(examService, times(1)).update(eq(99L), any(Exam.class));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        doNothing().when(examService).delete(1L);

        mockMvc.perform(delete("/api/exams/1"))
                .andExpect(status().isNoContent());

        verify(examService, times(1)).delete(1L);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        doThrow(new RuntimeException()).when(examService).delete(99L);

        mockMvc.perform(delete("/api/exams/99"))
                .andExpect(status().isNotFound());

        verify(examService, times(1)).delete(99L);
    }

    @Test
    public void testGetByExamPeriod() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        when(examService.getByExamPeriodId(5L)).thenReturn(Collections.singletonList(exam));

        mockMvc.perform(get("/api/exams/exam-period/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(examService, times(1)).getByExamPeriodId(5L);
    }

    @Test
    public void testGetByCourse() throws Exception {
        Exam exam = new Exam();
        exam.setId(2L);
        when(examService.getByCourseId(10L)).thenReturn(Collections.singletonList(exam));

        mockMvc.perform(get("/api/exams/course/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));

        verify(examService, times(1)).getByCourseId(10L);
    }

    @Test
    public void testGetByDateAndTenant() throws Exception {
        Exam exam = new Exam();
        exam.setId(3L);
        LocalDate date = LocalDate.of(2025, 5, 23);

        when(examService.getByExamDateAndTenantId(date, 7L)).thenReturn(Collections.singletonList(exam));

        mockMvc.perform(get("/api/exams/date-tenant")
                        .param("examDate", date.toString())
                        .param("tenantId", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));

        verify(examService, times(1)).getByExamDateAndTenantId(date, 7L);
    }
}
