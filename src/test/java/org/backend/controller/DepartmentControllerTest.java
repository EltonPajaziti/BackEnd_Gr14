package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.dto.DepartmentCreateDTO;
import org.backend.model.Department;
import org.backend.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Department dep = new Department();
        dep.setId(1L);
        dep.setName("Computer Science");

        when(departmentService.getAllDepartments()).thenReturn(List.of(dep));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Computer Science"));

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testGetDepartmentByIdFound() throws Exception {
        Department dep = new Department();
        dep.setId(1L);
        dep.setName("Mathematics");

        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(dep));

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mathematics"));

        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    public void testCreateDepartment() throws Exception {
        DepartmentCreateDTO dto = new DepartmentCreateDTO();
        dto.setName("Physics");

        Department created = new Department();
        created.setId(1L);
        created.setName("Physics");

        when(departmentService.createDepartment(any(DepartmentCreateDTO.class))).thenReturn(created);

        mockMvc.perform(post("/api/departments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Physics"));

        verify(departmentService, times(1)).createDepartment(any(DepartmentCreateDTO.class));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        DepartmentCreateDTO dto = new DepartmentCreateDTO();
        dto.setName("Updated Name");

        Department updated = new Department();
        updated.setId(1L);
        updated.setName("Updated Name");

        when(departmentService.updateDepartment(eq(1L), any(DepartmentCreateDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));

        verify(departmentService, times(1)).updateDepartment(eq(1L), any(DepartmentCreateDTO.class));
    }

    @Test
    public void testUpdateDepartmentNotFound() throws Exception {
        DepartmentCreateDTO dto = new DepartmentCreateDTO();
        dto.setName("Updated Name");

        when(departmentService.updateDepartment(eq(99L), any(DepartmentCreateDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/departments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

        verify(departmentService, times(1)).updateDepartment(eq(99L), any(DepartmentCreateDTO.class));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
