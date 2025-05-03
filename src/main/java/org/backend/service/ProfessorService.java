package org.backend.service;

import org.backend.model.Professor;
import org.backend.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor updateProfessor(Long id, Professor updatedProfessor) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesori nuk u gjet"));

        professor.setAcademicTitle(updatedProfessor.getAcademicTitle());
        professor.setHiredDate(updatedProfessor.getHiredDate());

        if (updatedProfessor.getUser() != null) {
            professor.setUser(updatedProfessor.getUser());
        }
        if (updatedProfessor.getDepartment() != null) {
            professor.setDepartment(updatedProfessor.getDepartment());
        }
        if (updatedProfessor.getTenantID() != null) {
            professor.setTenantID(updatedProfessor.getTenantID());
        }

        return professorRepository.save(professor);
    }

    public void deleteProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Profesori nuk ekziston");
        }
        professorRepository.deleteById(id);
    }
}
