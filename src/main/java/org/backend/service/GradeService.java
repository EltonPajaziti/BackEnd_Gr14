package org.backend.service;

import org.backend.model.Grade;
import org.backend.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Optional<Grade> getGradeById(Long id) {
        return gradeRepository.findById(id);
    }

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    // Metodë për update (nëse nevojitet)
    public Grade updateGrade(Long id, Grade gradeDetails) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        // Vendosim logjikën e update këtu
        grade.setGrade_value(gradeDetails.getGrade_value());
        grade.setGradedAt(gradeDetails.getGradedAt());
        // ... other fields to update
        return gradeRepository.save(grade);
    }
}