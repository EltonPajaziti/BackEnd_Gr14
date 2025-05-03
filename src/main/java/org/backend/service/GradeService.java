package org.backend.service;

import org.backend.model.Grade;
import org.backend.repository.GradeRepository;

import java.util.List;
import java.util.Optional;

public class GradeService {

    private GradeRepository gradeRepository;

    public List<Grade> getAllGrades(){
        return gradeRepository.findAll();
    }
    public Optional<Grade> getGradeById(Long id){
        return gradeRepository.findById(id);
    }

    public Grade createGrade(Grade grade){
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    //public Grade updateGrade(Long id, Grade updatedGrade)              ---- duhet te permirsohet se bashku me CourseService


}
