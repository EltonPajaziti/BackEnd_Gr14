package org.backend.controller;

import org.backend.model.GradeConfirmation;
import org.backend.service.GradeConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gradeconfirmations")
public class GradeConfirmationController {
    @Autowired
    private GradeConfirmationService gradeConfirmationService;

    @GetMapping
    public List<GradeConfirmation> getAllGradeConfirmations() {
        return gradeConfirmationService.getAllGradeConfirmations();
    }

    @GetMapping("/{id}")
    public GradeConfirmation getGradeConfirmationById(@PathVariable Long id) {
        return gradeConfirmationService.getGradeConfirmationById(id).orElseThrow();
    }

    @PostMapping
    public GradeConfirmation createGradeConfirmation(@RequestBody GradeConfirmation gradeConfirmation) {
        return gradeConfirmationService.createGradeConfirmation(gradeConfirmation);
    }

    @DeleteMapping("/{id}")
    public void deleteGradeConfirmation(@PathVariable Long id) {
        gradeConfirmationService.deleteGradeConfirmation(id);
    }

    @GetMapping("/expires/{date}")
    public GradeConfirmation getByExpirationDate(@PathVariable LocalDate date) {
        return gradeConfirmationService.findByExpiresAt(date);
    }
}