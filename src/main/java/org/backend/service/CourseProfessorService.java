package org.backend.service;

import org.backend.model.Course_Professor;
import org.backend.repository.CourseProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseProfessorService {

    private final CourseProfessorRepository courseProfessorRepository;

    public CourseProfessorService(CourseProfessorRepository courseProfessorRepository) {
        this.courseProfessorRepository = courseProfessorRepository;
    }

    public Course_Professor create(Course_Professor courseProfessor) {
        return courseProfessorRepository.save(courseProfessor);
    }

    public List<Course_Professor> getAll() {
        return courseProfessorRepository.findAll();
    }

    public Optional<Course_Professor> getById(Long id) {
        return courseProfessorRepository.findById(id);
    }

    public List<Course_Professor> getByTenantId(Long tenantId) {
        return courseProfessorRepository.findByTenantID_Id(tenantId);
    }

    public void delete(Long id) {
        courseProfessorRepository.deleteById(id);
    }
}
