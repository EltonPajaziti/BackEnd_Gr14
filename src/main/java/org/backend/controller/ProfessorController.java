package org.backend.controller;

import org.backend.model.Professor;
import org.backend.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@CrossOrigin(origins = "*")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        return ResponseEntity.ok(professorService.createProfessor(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        return ResponseEntity.ok(professorService.updateProfessor(id, professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/count/by-tenant/{tenantId}")
    public Long countProfessorsByTenant(@PathVariable("tenantId") Long tenantId) {
        return professorService.countProfessorsByTenant(tenantId);
    }
}
