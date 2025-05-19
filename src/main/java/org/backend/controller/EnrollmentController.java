package org.backend.controller;

import org.backend.dto.EnrollmentRequestDTO;
import org.backend.dto.RegisteredCourseDTO;
import org.backend.model.Enrollment;
import org.backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*") // nëse ke frontend në localhost:5173 ose tjetër
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // GET all
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Optional<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    // GET by Student
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    // POST new enrollment with full Enrollment object
    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.createEnrollment(enrollment);
    }

    // POST enroll via DTO
    @PostMapping("/register")
    public Enrollment enrollStudent(@RequestBody EnrollmentRequestDTO dto) {
        return enrollmentService.enrollStudent(dto);
    }

    // PUT update
    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }

//    @GetMapping("/student/{studentId}/registered-courses")
//    public List<Enrollment> getRegisteredCourses(@PathVariable("studentId") Long studentId)
//    {
//        return enrollmentService.getEnrollmentsByStudent(studentId);
//    }



    @GetMapping("/student/{studentId}/registered-courses")
    public List<RegisteredCourseDTO> getRegisteredCourses(@PathVariable("studentId") Long studentId)
 {
        return enrollmentService.getRegisteredCoursesForStudent(studentId);
    }


}
