package org.backend.service;

import org.backend.model.Exam_Period;
import org.backend.repository.ExamPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamPeriodService {

    @Autowired
    private ExamPeriodRepository examPeriodRepository;

    public List<Exam_Period> getAll() {
        return examPeriodRepository.findAll();
    }

    public Optional<Exam_Period> getById(Long id) {
        return examPeriodRepository.findById(id);
    }

    public Exam_Period create(Exam_Period examPeriod) {
        return examPeriodRepository.save(examPeriod);
    }

    public Exam_Period update(Long id, Exam_Period updated) {
        Exam_Period existing = examPeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam period not found"));

        existing.setName(updated.getName());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setRegistrationStart(updated.getRegistrationStart());
        existing.setRegistrationEnd(updated.getRegistrationEnd());
        existing.setIsActive(updated.getIsActive());
        existing.setAcademicYear(updated.getAcademicYear());
        existing.setTenant(updated.getTenant());

        return examPeriodRepository.save(existing);
    }

    public void delete(Long id) {
        examPeriodRepository.deleteById(id);
    }
}
