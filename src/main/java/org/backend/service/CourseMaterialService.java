package org.backend.service;

import org.backend.model.CourseMaterial;
import org.backend.repository.CourseMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseMaterialService {

    private final CourseMaterialRepository courseMaterialRepository;

    @Autowired
    public CourseMaterialService(CourseMaterialRepository courseMaterialRepository) {
        this.courseMaterialRepository = courseMaterialRepository;
    }

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
}