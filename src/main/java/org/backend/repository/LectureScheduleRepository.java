package org.backend.repository;

import org.backend.model.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {
    List<LectureSchedule> findByTenantId(Long tenantId);
    List<LectureSchedule> findByCourseProfessorId(Long courseProfessorId);
    List<LectureSchedule> findByProgramId(Long programId);
    List<LectureSchedule> findByTenantIdAndProgramId(Long tenantId, Long programId);
}