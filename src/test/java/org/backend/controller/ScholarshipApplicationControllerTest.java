package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.AcademicYear;
import org.backend.model.Faculty;
import org.backend.model.ScholarshipApplication;
import org.backend.model.Student;
import org.backend.service.ScholarshipApplicationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ScholarshipApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScholarshipApplicationService scholarshipApplicationService;

    @InjectMocks
    private ScholarshipApplicationController scholarshipApplicationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Student createDummyStudent() {
        Student student = new Student();
        student.setId(1L);
        return student;
    }

    private AcademicYear createDummyAcademicYear() {
        AcademicYear academicYear = new AcademicYear();
        academicYear.setId(1L);
        return academicYear;
    }

    private Faculty createDummyFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        return faculty;
    }

    private ScholarshipApplication createValidApplication() {
        ScholarshipApplication app = new ScholarshipApplication();
        app.setId(1L);
        app.setStudent(createDummyStudent());
        app.setAcademicYear(createDummyAcademicYear());
        app.setTenantID(createDummyFaculty());
        app.setGpa(3.7);
        app.setStatus("Pending");
        return app;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scholarshipApplicationController).build();
    }

    @Test
    void testGetAll() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.getAll()).thenReturn(List.of(app));

        mockMvc.perform(get("/api/scholarship-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(app.getId()));
    }

    @Test
    void testGetById_Found() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.getById(1L)).thenReturn(Optional.of(app));

        mockMvc.perform(get("/api/scholarship-applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(app.getId()));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(scholarshipApplicationService.getById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/scholarship-applications/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate_Valid() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.create(any(ScholarshipApplication.class))).thenReturn(app);

        mockMvc.perform(post("/api/scholarship-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(app.getId()));
    }

    @Test
    void testCreate_Invalid() throws Exception {
        // Një objekt bosh ose pa fushat required
        ScholarshipApplication invalidApp = new ScholarshipApplication();

        mockMvc.perform(post("/api/scholarship-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidApp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_Valid() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.update(eq(1L), any(ScholarshipApplication.class))).thenReturn(app);

        String json = objectMapper.writeValueAsString(app);

        mockMvc.perform(put("/api/scholarship-applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(app.getId()));
    }

    @Test
    void testUpdate_Invalid() throws Exception {
        ScholarshipApplication invalidApp = new ScholarshipApplication();

        mockMvc.perform(put("/api/scholarship-applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidApp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.update(eq(1L), any(ScholarshipApplication.class)))
                .thenThrow(new RuntimeException("Scholarship application not found"));

        String json = objectMapper.writeValueAsString(app);

        mockMvc.perform(put("/api/scholarship-applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(scholarshipApplicationService).delete(1L);

        mockMvc.perform(delete("/api/scholarship-applications/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        doThrow(new RuntimeException("Scholarship application not found")).when(scholarshipApplicationService).delete(1L);

        mockMvc.perform(delete("/api/scholarship-applications/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByStudent() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.getByStudentId(5L)).thenReturn(List.of(app));

        mockMvc.perform(get("/api/scholarship-applications/student/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(app.getId()));
    }

    @Test
    void testGetByAcademicYear() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.getByAcademicYearId(2L)).thenReturn(List.of(app));

        mockMvc.perform(get("/api/scholarship-applications/academic-year/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(app.getId()));
    }

    @Test
    void testGetByTenant() throws Exception {
        ScholarshipApplication app = createValidApplication();
        when(scholarshipApplicationService.getByTenantId(3L)).thenReturn(List.of(app));

        mockMvc.perform(get("/api/scholarship-applications/tenant/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(app.getId()));
    }
}
