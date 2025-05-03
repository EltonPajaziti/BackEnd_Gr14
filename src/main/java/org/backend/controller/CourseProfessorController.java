package org.backend.controller;

import org.backend.model.Course_Professor;
import org.backend.service.CourseProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-professors")
public class CourseProfessorController {

    private final CourseProfessorService courseProfessorService;

    public CourseProfessorController(CourseProfessorService courseProfessorService) {
        this.courseProfessorService = courseProfessorService;
    }

    @PostMapping
    public ResponseEntity<Course_Professor> create(@RequestBody Course_Professor cp) {
        return ResponseEntity.ok(courseProfessorService.create(cp));
    }

    @GetMapping
    public ResponseEntity<List<Course_Professor>> getAll() {
        return ResponseEntity.ok(courseProfessorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course_Professor> getById(@PathVariable Long id) {
        return courseProfessorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Course_Professor>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(courseProfessorService.getByTenantId(tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseProfessorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
