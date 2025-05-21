package org.backend.controller;

import org.backend.dto.CourseDTO;
import org.backend.model.Student;
import org.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentsController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id).orElseThrow();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/by-tenant/{tenantId}")
    public List<Student> getStudentsByTenant(@PathVariable("tenantId") Long tenantId) {
        return studentService.getStudentsByTenant(tenantId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/count/by-tenant/{tenantId}")
    public long countStudentsByTenant(@PathVariable("tenantId") Long tenantId) {
        return studentService.countStudentsByTenant(tenantId);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Student> getStudentByUserId(@PathVariable("userId") Long userId) {
        return studentService.getStudentByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{studentId}/available-courses")
    public ResponseEntity<List<CourseDTO>> getAvailableCourses(
            @PathVariable("studentId") Long studentId,
            @RequestParam("semester") Short semester) {
        List<CourseDTO> courses = studentService.getAvailableCourses(studentId, semester);
        return ResponseEntity.ok(courses);
    }


}
