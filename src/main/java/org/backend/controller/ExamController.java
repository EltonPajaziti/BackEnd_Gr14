package org.backend.controller;

import org.backend.model.Exam;
import org.backend.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public List<Exam> getAll() {
        return examService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable Long id) {
        return examService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Exam> create(@Valid @RequestBody Exam exam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(examService.create(exam));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> update(@PathVariable Long id, @Valid @RequestBody Exam exam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(examService.update(id, exam));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            examService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exam-period/{examPeriodId}")
    public List<Exam> getByExamPeriod(@PathVariable Long examPeriodId) {
        return examService.getByExamPeriodId(examPeriodId);
    }

    @GetMapping("/course/{courseId}")
    public List<Exam> getByCourse(@PathVariable Long courseId) {
        return examService.getByCourseId(courseId);
    }

    @GetMapping("/date-tenant")
    public List<Exam> getByDateAndTenant(
            @RequestParam LocalDate examDate,
            @RequestParam Long tenantId) {
        return examService.getByExamDateAndTenantId(examDate, tenantId);
    }
}
