package org.backend.repository;

import org.backend.model.Course_Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseProfessorRepository extends JpaRepository<Course_Professor, Long> {
    List<Course_Professor> findByTenantID_Id(Long tenantId);
}
