package org.backend.service;

import org.backend.dto.CourseMaterialDTO;
import org.backend.model.Course;
import org.backend.model.CourseMaterial;
import org.backend.model.Course_Professor;
import org.backend.model.Professor;
import org.backend.repository.CourseMaterialRepository;
import org.backend.repository.CourseProfessorRepository;
import org.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseMaterialService {

    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseRepository courseRepository;
    private final CourseProfessorRepository courseProfessorRepository;

    @Autowired
    public CourseMaterialService(CourseMaterialRepository courseMaterialRepository,
                                 CourseRepository courseRepository,
                                 CourseProfessorRepository courseProfessorRepository) {
        this.courseMaterialRepository = courseMaterialRepository;
        this.courseRepository = courseRepository;
        this.courseProfessorRepository = courseProfessorRepository;
    }

    // CRUD operacionet ekzistuese

    @Transactional(readOnly = true)
    public List<CourseMaterial> getAll() {
        return courseMaterialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CourseMaterial> getById(Long id) {
        return courseMaterialRepository.findById(id);
    }

    @Transactional
    public CourseMaterial create(CourseMaterial courseMaterial) {
        validateCourseMaterial(courseMaterial);
        return courseMaterialRepository.save(courseMaterial);
    }

    @Transactional
    public CourseMaterial update(Long id, CourseMaterial updatedCourseMaterial) {
        validateCourseMaterial(updatedCourseMaterial);
        CourseMaterial existingCourseMaterial = courseMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course material not found"));
        updatedCourseMaterial.setId(id);
        return courseMaterialRepository.save(updatedCourseMaterial);
    }

    @Transactional
    public void delete(Long id) {
        if (!courseMaterialRepository.existsById(id)) {
            throw new RuntimeException("Course material not found");
        }
        courseMaterialRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CourseMaterial> getByTenantId(Long tenantId) {
        return courseMaterialRepository.findByTenantIDId(tenantId);
    }

    private void validateCourseMaterial(CourseMaterial courseMaterial) {
        if (courseMaterial.getTitle() == null || courseMaterial.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (courseMaterial.getFileUrl() == null || courseMaterial.getFileUrl().isBlank()) {
            throw new IllegalArgumentException("File URL is required");
        }
        if (courseMaterial.getTenantID() == null || courseMaterial.getTenantID().getId() == null) {
            throw new IllegalArgumentException("Tenant (Faculty) is required");
        }
    }

    // DTO logjika shtesë për frontend


//    @Cacheable(value = "courseMaterials", key = "#courseId")  //CACHE
@Cacheable(value = "courseMaterials", key = "T(java.lang.String).valueOf(#p0)") // CACHE

@Transactional(readOnly = true)
    public List<CourseMaterialDTO> getMaterialsByCourseId(Long courseId) {
        List<CourseMaterial> materials = courseMaterialRepository.findByCourseId(courseId);

        if (materials.isEmpty()) {
            return new ArrayList<>();
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Lënda nuk u gjet"));

        String professorName = "N/A";
        List<Course_Professor> cpList = courseProfessorRepository.findByCourseId(courseId);
        if (!cpList.isEmpty()) {
            Professor professor = cpList.get(0).getProfessor();
            if (professor != null && professor.getUser() != null) {
                professorName = professor.getUser().getFirstName() + " " + professor.getUser().getLastName();
            }
        }

        List<CourseMaterialDTO> dtos = new ArrayList<>();
        for (CourseMaterial material : materials) {
            CourseMaterialDTO dto = new CourseMaterialDTO();
            dto.setCourseName(course.getName());
            dto.setCourseCode(course.getCode());
            dto.setProfessorName(professorName);
            dto.setTitle(material.getTitle());
            dto.setDescription(material.getDescription());
            dto.setFileUrl(material.getFileUrl());
            dto.setUploadedAt(material.getUploadedAt());

            dtos.add(dto);
        }

        return dtos;
    }
}
