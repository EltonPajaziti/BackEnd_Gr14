package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.Course_Professor;
import org.backend.service.CourseProfessorService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseProfessorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseProfessorService courseProfessorService;

    @InjectMocks
    private CourseProfessorController courseProfessorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseProfessorController).build();
    }

    @Test
    public void testCreate() throws Exception {
        Course_Professor cp = new Course_Professor();
        cp.setId(1L);


        when(courseProfessorService.create(any(Course_Professor.class))).thenReturn(cp);

        mockMvc.perform(post("/api/course-professors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(courseProfessorService, times(1)).create(any(Course_Professor.class));
    }

    @Test
    public void testGetAll() throws Exception {
        Course_Professor cp = new Course_Professor();
        cp.setId(1L);


        when(courseProfessorService.getAll()).thenReturn(List.of(cp));

        mockMvc.perform(get("/api/course-professors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(courseProfessorService, times(1)).getAll();
    }

    @Test
    public void testGetByIdFound() throws Exception {
        Course_Professor cp = new Course_Professor();
        cp.setId(1L);


        when(courseProfessorService.getById(1L)).thenReturn(Optional.of(cp));

        mockMvc.perform(get("/api/course-professors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(courseProfessorService, times(1)).getById(1L);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(courseProfessorService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/course-professors/99"))
                .andExpect(status().isNotFound());

        verify(courseProfessorService, times(1)).getById(99L);
    }

    @Test
    public void testGetByTenantId() throws Exception {
        Course_Professor cp = new Course_Professor();
        cp.setId(1L);

        when(courseProfessorService.getByTenantId(10L)).thenReturn(List.of(cp));

        mockMvc.perform(get("/api/course-professors/tenant/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(courseProfessorService, times(1)).getByTenantId(10L);
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(courseProfessorService).delete(1L);

        mockMvc.perform(delete("/api/course-professors/1"))
                .andExpect(status().isNoContent());

        verify(courseProfessorService, times(1)).delete(1L);
    }
}
