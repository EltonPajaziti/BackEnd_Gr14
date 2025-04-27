package org.backend.controller;

import org.backend.model.AcademicYear;
import org.backend.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping
    public List<AcademicYear> getAll() {
        return academicYearService.getAll();
    }

    @GetMapping("/{id}")
    public AcademicYear getById(@PathVariable Long id) {
        return academicYearService.getById(id).orElseThrow();
    }

    @PostMapping
    public AcademicYear create(@RequestBody AcademicYear academicYear) {
        return academicYearService.create(academicYear);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> update(@PathVariable Long id, @RequestBody AcademicYear updated) {
        try {
            return ResponseEntity.ok(academicYearService.update(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        academicYearService.delete(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<AcademicYear> getByTenant(@PathVariable Long tenantId) {
        return academicYearService.getByTenantId(tenantId);
    }

    @GetMapping("/active")
    public List<AcademicYear> getActiveYears() {
        return academicYearService.getActiveYears();
    }
}
