package org.backend.mapper;

import org.backend.dto.CourseDTO;
import org.backend.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course, String professorName) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setCredits(course.getCredits());
        dto.setProfessorName(professorName);
        return dto;
    }
}
