package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.dto.FacultyCreateDTO;
import org.backend.model.Faculty;
import org.backend.service.FacultyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FacultyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        Faculty f1 = new Faculty();
        f1.setId(1L);
        f1.setName("Faculty A");
        Faculty f2 = new Faculty();
        f2.setId(2L);
        f2.setName("Faculty B");

        when(facultyService.getAllFaculties()).thenReturn(Arrays.asList(f1, f2));

        mockMvc.perform(get("/api/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Faculty B"));

        verify(facultyService, times(1)).getAllFaculties();
    }

    @Test
    public void testGetFacultyByIdFound() throws Exception {
        Faculty f = new Faculty();
        f.setId(1L);
        f.setName("Faculty A");

        when(facultyService.getFacultyById(1L)).thenReturn(Optional.of(f));

        mockMvc.perform(get("/api/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty A"));

        verify(facultyService, times(1)).getFacultyById(1L);
    }

    @Test
    public void testGetFacultyByIdNotFound() throws Exception {
        when(facultyService.getFacultyById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/faculties/99"))
                .andExpect(status().isNotFound());

        verify(facultyService, times(1)).getFacultyById(99L);
    }


    @Test
    public void testCreateFaculty() throws Exception {
        FacultyCreateDTO dto = new FacultyCreateDTO();
        dto.setName("New Faculty");

        Faculty created = new Faculty();
        created.setId(1L);
        created.setName("New Faculty");

        when(facultyService.createFaculty(any(FacultyCreateDTO.class))).thenReturn(created);

        mockMvc.perform(post("/api/faculties/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Faculty"));

        verify(facultyService, times(1)).createFaculty(any(FacultyCreateDTO.class));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty updated = new Faculty();
        updated.setId(1L);
        updated.setName("Updated Faculty");

        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(updated);

        mockMvc.perform(put("/api/faculties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Faculty"));

        verify(facultyService, times(1)).updateFaculty(eq(1L), any(Faculty.class));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1L);

        mockMvc.perform(delete("/api/faculties/1"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).deleteFaculty(1L);
    }
}
