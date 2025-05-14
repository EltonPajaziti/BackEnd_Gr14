package org.backend.repository;

import org.backend.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Long countByTenantID_Id(Long tenantId);
}
