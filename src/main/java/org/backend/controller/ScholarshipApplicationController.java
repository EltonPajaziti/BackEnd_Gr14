package org.backend.controller;

import org.backend.model.ScholarshipApplication;
import org.backend.service.ScholarshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/scholarship-applications")
@CrossOrigin(origins = "*")
public class ScholarshipApplicationController {

    private final ScholarshipApplicationService scholarshipApplicationService;

    @Autowired
    public ScholarshipApplicationController(ScholarshipApplicationService scholarshipApplicationService) {
        this.scholarshipApplicationService = scholarshipApplicationService;
    }

    @GetMapping
    public List<ScholarshipApplication> getAll() {
        return scholarshipApplicationService.getAll();
    }

    @GetMapping("/{id}")
    public ScholarshipApplication getById(@PathVariable Long id) {
        return scholarshipApplicationService.getById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship application not found"));
    }

    @PostMapping
    public ResponseEntity<ScholarshipApplication> create(@Valid @RequestBody ScholarshipApplication scholarshipApplication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(scholarshipApplicationService.create(scholarshipApplication));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScholarshipApplication> update(@PathVariable Long id, @Valid @RequestBody ScholarshipApplication scholarshipApplication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(scholarshipApplicationService.update(id, scholarshipApplication));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            scholarshipApplicationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public List<ScholarshipApplication> getByStudent(@PathVariable Long studentId) {
        return scholarshipApplicationService.getByStudentId(studentId);
    }

    @GetMapping("/academic-year/{academicYearId}")
    public List<ScholarshipApplication> getByAcademicYear(@PathVariable Long academicYearId) {
        return scholarshipApplicationService.getByAcademicYearId(academicYearId);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<ScholarshipApplication> getByTenant(@PathVariable Long tenantId) {
        return scholarshipApplicationService.getByTenantId(tenantId);
    }
}