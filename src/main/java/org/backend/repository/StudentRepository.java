package org.backend.repository;

import org.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTenantID_Id(Long tenantId);

    long countByTenantID_Id(Long tenantId);

    Optional<Student> findByUser_Id(Long userId);
}
