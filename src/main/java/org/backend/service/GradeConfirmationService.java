package org.backend.service;

import org.backend.model.GradeConfirmation;
import org.backend.repository.GradeConfirmationRepository;


import java.util.List;
import java.util.Optional;

public class GradeConfirmationService {

    private GradeConfirmationRepository gradeConfirmationRepository;

    public List<GradeConfirmation> getAllGradeConfirmations(){
        return gradeConfirmationRepository.findAll();
    }
    public Optional<GradeConfirmation> getGradeConfirmationById(Long id){
        return gradeConfirmationRepository.findById(id);
    }

    public GradeConfirmation createGradeConfirmation(GradeConfirmation gradeConfirmation){
        return gradeConfirmationRepository.save(gradeConfirmation);
    }

    public void deleteGradeConfirmation(Long id) {
        gradeConfirmationRepository.deleteById(id);
    }
}
