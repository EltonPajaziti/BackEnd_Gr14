package org.backend.controller;

import org.backend.model.Course;
import org.backend.repository.CourseRepository;
import org.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id).orElseThrow();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        try {
            Course updated = courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/program/{programId}")
    public List<Course> getCoursesByProgram(@PathVariable Long programId) {
        return courseService.getCoursesByProgramId(programId);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/count/by-tenant/{tenantId}")
    public Long countCoursesByTenant(@PathVariable("tenantId") Long tenantId) {
        return courseService.countCourseByTenant(tenantId);
    }

//    @GetMapping("/available/{studentId}/{semester}")
//    public ResponseEntity<List<Course>> getAvailableCourses(
//            @PathVariable Long studentId,
//            @PathVariable Short semester) {
//        List<Course> courses = courseService.getAvailableCoursesForStudent(studentId, semester);
//        return ResponseEntity.ok(courses);
//    }

//    @GetMapping("/semesters/{tenantId}")
//    public ResponseEntity<List<Short>> getAvailableSemesters(@PathVariable Long tenantId) {
//        return ResponseEntity.ok(courseService.getAvailableSemesters(tenantId));
//    }
}
