package org.backend.repository;

import org.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTenantID_Id(Long tenantId);
}
