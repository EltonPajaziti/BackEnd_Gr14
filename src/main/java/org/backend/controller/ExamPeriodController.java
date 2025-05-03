package org.backend.controller;

import org.backend.model.Exam_Period;
import org.backend.service.ExamPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-periods")
public class ExamPeriodController {

    @Autowired
    private ExamPeriodService examPeriodService;

    @GetMapping
    public List<Exam_Period> getAll() {
        return examPeriodService.getAll();
    }

    @GetMapping("/{id}")
    public Exam_Period getById(@PathVariable Long id) {
        return examPeriodService.getById(id).orElseThrow();
    }

    @PostMapping
    public Exam_Period create(@RequestBody Exam_Period examPeriod) {
        return examPeriodService.create(examPeriod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam_Period> update(@PathVariable Long id, @RequestBody Exam_Period updated) {
        try {
            return ResponseEntity.ok(examPeriodService.update(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        examPeriodService.delete(id);
    }
}
