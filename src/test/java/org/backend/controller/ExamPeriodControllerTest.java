package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.Exam_Period;
import org.backend.service.ExamPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExamPeriodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExamPeriodService examPeriodService;

    @InjectMocks
    private ExamPeriodController examPeriodController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(examPeriodController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAll() throws Exception {
        Exam_Period ep1 = new Exam_Period();
        ep1.setId(1L);
        Exam_Period ep2 = new Exam_Period();
        ep2.setId(2L);

        when(examPeriodService.getAll()).thenReturn(Arrays.asList(ep1, ep2));

        mockMvc.perform(get("/api/exam-periods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(examPeriodService, times(1)).getAll();
    }

    @Test
    public void testGetByIdFound() throws Exception {
        Exam_Period ep = new Exam_Period();
        ep.setId(1L);

        when(examPeriodService.getById(1L)).thenReturn(Optional.of(ep));

        mockMvc.perform(get("/api/exam-periods/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examPeriodService, times(1)).getById(1L);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(examPeriodService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/exam-periods/99"))
                .andExpect(status().isNotFound());

        verify(examPeriodService, times(1)).getById(99L);
    }

    @Test
    public void testCreate() throws Exception {
        Exam_Period ep = new Exam_Period();
        ep.setId(1L);

        when(examPeriodService.create(any(Exam_Period.class))).thenReturn(ep);

        mockMvc.perform(post("/api/exam-periods")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(ep)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examPeriodService, times(1)).create(any(Exam_Period.class));
    }

    @Test
    public void testUpdateFound() throws Exception {
        Exam_Period updated = new Exam_Period();
        updated.setId(1L);

        when(examPeriodService.update(eq(1L), any(Exam_Period.class))).thenReturn(updated);

        mockMvc.perform(put("/api/exam-periods/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(examPeriodService, times(1)).update(eq(1L), any(Exam_Period.class));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Exam_Period updated = new Exam_Period();

        when(examPeriodService.update(eq(99L), any(Exam_Period.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/exam-periods/99")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());

        verify(examPeriodService, times(1)).update(eq(99L), any(Exam_Period.class));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        doNothing().when(examPeriodService).delete(1L);

        mockMvc.perform(delete("/api/exam-periods/1"))
                .andExpect(status().isOk());  // sepse metoda delete void -> default 200 OK

        verify(examPeriodService, times(1)).delete(1L);
    }

}
