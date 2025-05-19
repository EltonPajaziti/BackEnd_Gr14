package org.backend.repository;

import org.backend.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseByName(String name);
    Course findCourseByCode(String code);
    List<Course> findByProgramId(Long programId);
    Long countByTenantID_Id(Long tenantId);

    List<Course> findByProgram_IdAndTenantID_IdAndSemester(
            Long programId, Long tenantId, Short semester);

    List<Course> findDistinctByTenantID_IdOrderBySemesterAsc(Long tenantId);

}
