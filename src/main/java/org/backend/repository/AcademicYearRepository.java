package org.backend.repository;

import org.backend.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    List<AcademicYear> findByTenantID_Id(Long tenantId);// nje rregullim i vogel

    List<AcademicYear> findByIsActiveTrue();
}
