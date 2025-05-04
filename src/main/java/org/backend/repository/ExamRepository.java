package org.backend.repository;

import org.backend.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByExamPeriodId(Long examPeriodId);
    List<Exam> findByCourseId(Long courseId);
    List<Exam> findByExamDateAndTenantId(LocalDate examDate, Long tenantId);
}