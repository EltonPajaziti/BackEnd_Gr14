package org.backend.service;

import org.backend.model.Exam;
import org.backend.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    @Autowired
    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Transactional(readOnly = true)
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Exam> getById(Long id) {
        return examRepository.findById(id);
    }

    @Transactional
    public Exam create(Exam exam) {
        validateExam(exam);
        return examRepository.save(exam);
    }

    @Transactional
    public Exam update(Long id, Exam updatedExam) {
        validateExam(updatedExam);
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        updatedExam.setId(id);
        return examRepository.save(updatedExam);
    }

    @Transactional
    public void delete(Long id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found");
        }
        examRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Exam> getByExamPeriodId(Long examPeriodId) {
        return examRepository.findByExamPeriodId(examPeriodId);
    }

    @Transactional(readOnly = true)
    public List<Exam> getByCourseId(Long courseId) {
        return examRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Exam> getByExamDateAndTenantId(LocalDate examDate, Long tenantId) {
        return examRepository.findByExamDateAndTenantId(examDate, tenantId);
    }

    private void validateExam(Exam exam) {
        if (exam.getCourse() == null || exam.getCourse().getId() == null) {
            throw new IllegalArgumentException("Course is required");
        }
        if (exam.getExamPeriod() == null || exam.getExamPeriod().getId() == null) {
            throw new IllegalArgumentException("Exam period is required");
        }
        if (exam.getExamDate() == null) {
            throw new IllegalArgumentException("Exam date is required");
        }
        if (exam.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is required");
        }
        if (exam.getEndTime() == null) {
            throw new IllegalArgumentException("End time is required");
        }
        if (exam.getExamType() == null) {
            throw new IllegalArgumentException("Exam type is required");
        }
        if (exam.getTenant() == null || exam.getTenant().getId() == null) {
            throw new IllegalArgumentException("Tenant (Faculty) is required");
        }
    }
}