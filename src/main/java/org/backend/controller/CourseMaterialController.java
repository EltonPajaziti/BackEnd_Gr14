package org.backend.controller;

import org.backend.dto.CourseMaterialDTO;
import org.backend.model.CourseMaterial;
import org.backend.service.CourseMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course-materials")
@CrossOrigin(origins = "*")
public class CourseMaterialController {

    private final CourseMaterialService courseMaterialService;

    @Autowired
    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping
    public List<CourseMaterial> getAll() {
        return courseMaterialService.getAll();
    }

    @GetMapping("/{id}")
    public CourseMaterial getById(@PathVariable Long id) {
        return courseMaterialService.getById(id)
                .orElseThrow(() -> new RuntimeException("Course material not found"));
    }

    @PostMapping
    public ResponseEntity<CourseMaterial> create(@Valid @RequestBody CourseMaterial courseMaterial, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(courseMaterialService.create(courseMaterial));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseMaterial> update(@PathVariable Long id, @Valid @RequestBody CourseMaterial courseMaterial, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(courseMaterialService.update(id, courseMaterial));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            courseMaterialService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public List<CourseMaterial> getByTenant(@PathVariable Long tenantId) {
        return courseMaterialService.getByTenantId(tenantId);
    }

    //  ENDPOINT I RI për materialet e një lënde të caktuar
    @GetMapping("/by-course")
    public ResponseEntity<List<CourseMaterialDTO>> getMaterialsByCourse(@RequestParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("courseId is required");
        }

        List<CourseMaterialDTO> materials = courseMaterialService.getMaterialsByCourseId(courseId);
        return ResponseEntity.ok(materials);
    }
}
