package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.Course;
import org.backend.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Math");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Physics");

        when(courseService.getAllCourses()).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetCourseById() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Math");

        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Biology");

        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Biology"));
    }

    @Test
    public void testUpdateCourseSuccess() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Updated Name");

        when(courseService.updateCourse(eq(1L), any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    public void testUpdateCourseNotFound() throws Exception {
        when(courseService.updateCourse(eq(999L), any(Course.class))).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/courses/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Course())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCoursesByProgram() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Chemistry");

        when(courseService.getCoursesByProgramId(100L)).thenReturn(List.of(course));

        mockMvc.perform(get("/api/courses/program/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chemistry"));
    }

    @Test
    public void testCountCoursesByTenant() throws Exception {
        when(courseService.countCourseByTenant(200L)).thenReturn(5L);

        mockMvc.perform(get("/api/courses/count/by-tenant/200"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
