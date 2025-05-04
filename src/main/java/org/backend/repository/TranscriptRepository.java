package org.backend.repository;

import org.backend.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {
    List<Transcript> findByStudentId(Long studentId);
    List<Transcript> findByTenantId(Long tenantId);
    List<Transcript> findByAcademicYearId(Long academicYearId);
}