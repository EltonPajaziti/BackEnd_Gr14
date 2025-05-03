package org.backend.service;

import org.backend.model.GradeConfirmation;
import org.backend.repository.GradeConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GradeConfirmationService {
    @Autowired
    private GradeConfirmationRepository gradeConfirmationRepository;

    public List<GradeConfirmation> getAllGradeConfirmations() {
        return gradeConfirmationRepository.findAll();
    }

    public Optional<GradeConfirmation> getGradeConfirmationById(Long id) {
        return gradeConfirmationRepository.findById(id);
    }

    public GradeConfirmation createGradeConfirmation(GradeConfirmation gradeConfirmation) {
        return gradeConfirmationRepository.save(gradeConfirmation);
    }

    public void deleteGradeConfirmation(Long id) {
        gradeConfirmationRepository.deleteById(id);
    }

    public GradeConfirmation findByExpiresAt(LocalDate expiresAt) {
        return gradeConfirmationRepository.findGradeConfirmationByExpiresAt(expiresAt);
    }
}