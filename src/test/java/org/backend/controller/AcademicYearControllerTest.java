package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.AcademicYear;
import org.backend.model.Faculty;
import org.backend.service.AcademicYearService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AcademicYearControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AcademicYearService academicYearService;

    @InjectMocks
    private AcademicYearController academicYearController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(academicYearController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    public void testGetAll() throws Exception {
        AcademicYear ay1 = new AcademicYear();
        ay1.setId(1L);
        ay1.setName("2023/2024");
        ay1.setStartDate(LocalDate.of(2023, 9, 1));
        ay1.setEndDate(LocalDate.of(2024, 6, 30));
        ay1.setIsActive(true);

        AcademicYear ay2 = new AcademicYear();
        ay2.setId(2L);
        ay2.setName("2024/2025");
        ay2.setStartDate(LocalDate.of(2024, 9, 1));
        ay2.setEndDate(LocalDate.of(2025, 6, 30));
        ay2.setIsActive(false);

        when(academicYearService.getAll()).thenReturn(Arrays.asList(ay1, ay2));

        mockMvc.perform(get("/api/academic-years"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetById() throws Exception {
        AcademicYear ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2023/2024");
        ay.setStartDate(LocalDate.of(2023, 9, 1));
        ay.setEndDate(LocalDate.of(2024, 6, 30));
        ay.setIsActive(true);

        when(academicYearService.getById(1L)).thenReturn(Optional.of(ay));

        mockMvc.perform(get("/api/academic-years/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("2023/2024"));
    }

    @Test
    public void testCreate() throws Exception {
        AcademicYear ay = new AcademicYear();
        ay.setName("2023/2024");
        ay.setStartDate(LocalDate.of(2023, 9, 1));
        ay.setEndDate(LocalDate.of(2024, 6, 30));
        ay.setIsActive(true);
        ay.setId(1L);

        when(academicYearService.create(any(AcademicYear.class))).thenReturn(ay);

        mockMvc.perform(post("/api/academic-years")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ay)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("2023/2024"));
    }

    @Test
    public void testUpdate() throws Exception {
        AcademicYear updated = new AcademicYear();
        updated.setId(1L);
        updated.setName("2024/2025");
        updated.setStartDate(LocalDate.of(2024, 9, 1));
        updated.setEndDate(LocalDate.of(2025, 6, 30));
        updated.setIsActive(true);

        when(academicYearService.update(eq(1L), any(AcademicYear.class))).thenReturn(updated);

        mockMvc.perform(put("/api/academic-years/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("2024/2025"));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(academicYearService).delete(1L);

        mockMvc.perform(delete("/api/academic-years/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByTenant() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);

        AcademicYear ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2023/2024");
        ay.setTenantID(faculty);

        when(academicYearService.getByTenantId(1L)).thenReturn(Arrays.asList(ay));

        mockMvc.perform(get("/api/academic-years/tenant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetActiveYears() throws Exception {
        AcademicYear ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2023/2024");
        ay.setIsActive(true);

        when(academicYearService.getActiveYears()).thenReturn(Arrays.asList(ay));

        mockMvc.perform(get("/api/academic-years/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
