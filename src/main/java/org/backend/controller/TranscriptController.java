package org.backend.controller;

import org.backend.model.Transcript;
import org.backend.service.TranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transcripts")
@CrossOrigin(origins = "*")
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @GetMapping
    public List<Transcript> getAll() {
        return transcriptService.getAll();
    }

    @GetMapping("/{id}")
    public Transcript getById(@PathVariable Long id) {
        return transcriptService.getById(id)
                .orElseThrow(() -> new RuntimeException("Transcript not found"));
    }

    @PostMapping
    public ResponseEntity<Transcript> create(@Valid @RequestBody Transcript transcript, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(transcriptService.create(transcript));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transcript> update(@PathVariable Long id, @Valid @RequestBody Transcript transcript, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(transcriptService.update(id, transcript));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            transcriptService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public List<Transcript> getByStudent(@PathVariable Long studentId) {
        return transcriptService.getByStudentId(studentId);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Transcript> getByTenant(@PathVariable Long tenantId) {
        return transcriptService.getByTenantId(tenantId);
    }

    @GetMapping("/academic-year/{academicYearId}")
    public List<Transcript> getByAcademicYear(@PathVariable Long academicYearId) {
        return transcriptService.getByAcademicYearId(academicYearId);
    }
}