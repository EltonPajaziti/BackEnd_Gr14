package org.backend.repository;

import org.backend.model.ScholarshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipApplicationRepository extends JpaRepository<ScholarshipApplication, Long> {
    List<ScholarshipApplication> findByStudentId(Long studentId);
    List<ScholarshipApplication> findByAcademicYearId(Long academicYearId);
    List<ScholarshipApplication> findByTenantIDId(Long tenantId);
}