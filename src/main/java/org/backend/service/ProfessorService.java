package org.backend.service;

import org.backend.model.Department;
import org.backend.model.Faculty;
import org.backend.model.Professor;
import org.backend.model.Users;
import org.backend.repository.DepartmentRepository;
import org.backend.repository.FacultyRepository;
import org.backend.repository.ProfessorRepository;
import org.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {


    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;


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


        if (updatedProfessor.getUser() != null && updatedProfessor.getUser().getId() != null) {
            Users user = userRepository.findById(updatedProfessor.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setFirstName(updatedProfessor.getUser().getFirstName());
            user.setLastName(updatedProfessor.getUser().getLastName());
            user.setEmail(updatedProfessor.getUser().getEmail());

            userRepository.save(user);

            professor.setUser(user);
        }


        if (updatedProfessor.getDepartment() != null && updatedProfessor.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedProfessor.getDepartment().getId())
                    .orElse(null); // or throw
            professor.setDepartment(dept);
        }


        if (updatedProfessor.getTenantID() != null && updatedProfessor.getTenantID().getId() != null) {
            Faculty tenant = facultyRepository.findById(updatedProfessor.getTenantID().getId())
                    .orElse(null); // or throw
            professor.setTenantID(tenant);
        }
        return professorRepository.save(professor);
    }
    public Long countProfessorsByTenant(Long tenantId) {
        return professorRepository.countByTenantID_Id(tenantId);
    }

    public void deleteProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Profesori nuk ekziston");
        }
        professorRepository.deleteById(id);
    }
}
