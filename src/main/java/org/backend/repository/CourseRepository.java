package org.backend.repository;

import org.backend.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseByName(String name);
    Course findCourseByCode(String code);
    List<Course> findByProgramId(Long programId);

}
