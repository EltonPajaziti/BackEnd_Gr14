package org.backend.repository;

import org.backend.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    List<AcademicYear> findByTenantId(Long tenantId);
    List<AcademicYear> findByIsActiveTrue();
}
