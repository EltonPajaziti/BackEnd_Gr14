package org.backend.controller;

import org.backend.model.Grade;
import org.backend.model.GradeConfirmation;
import org.backend.service.GradeConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    public GradeConfirmation createGradeConfirmation(@RequestBody GradeConfirmation gradeConfirmation) {
        return gradeConfirmationService.createGradeConfirmation(gradeConfirmation);
    }

    @DeleteMapping("/{id}")
    public void deleteGradeConfirmation(@PathVariable Long id) {
        gradeConfirmationService.deleteGradeConfirmation(id);
    }

}
