package org.backend.service;

import org.backend.model.ScholarshipApplication;
import org.backend.repository.ScholarshipApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScholarshipApplicationService {

    private final ScholarshipApplicationRepository scholarshipApplicationRepository;

    @Autowired
    public ScholarshipApplicationService(ScholarshipApplicationRepository scholarshipApplicationRepository) {
        this.scholarshipApplicationRepository = scholarshipApplicationRepository;
    }

    @Transactional(readOnly = true)
    public List<ScholarshipApplication> getAll() {
        return scholarshipApplicationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ScholarshipApplication> getById(Long id) {
        return scholarshipApplicationRepository.findById(id);
    }

    @Transactional
    public ScholarshipApplication create(ScholarshipApplication scholarshipApplication) {
        validateScholarshipApplication(scholarshipApplication);
        return scholarshipApplicationRepository.save(scholarshipApplication);
    }

    @Transactional
    public ScholarshipApplication update(Long id, ScholarshipApplication updatedScholarshipApplication) {
        validateScholarshipApplication(updatedScholarshipApplication);
        ScholarshipApplication existingScholarshipApplication = scholarshipApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship application not found"));
        updatedScholarshipApplication.setId(id);
        return scholarshipApplicationRepository.save(updatedScholarshipApplication);
    }

    @Transactional
    public void delete(Long id) {
        if (!scholarshipApplicationRepository.existsById(id)) {
            throw new RuntimeException("Scholarship application not found");
        }
        scholarshipApplicationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ScholarshipApplication> getByStudentId(Long studentId) {
        return scholarshipApplicationRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<ScholarshipApplication> getByAcademicYearId(Long academicYearId) {
        return scholarshipApplicationRepository.findByAcademicYearId(academicYearId);
    }

    @Transactional(readOnly = true)
    public List<ScholarshipApplication> getByTenantId(Long tenantId) {
        return scholarshipApplicationRepository.findByTenantIDId(tenantId);
    }

    private void validateScholarshipApplication(ScholarshipApplication scholarshipApplication) {
        if (scholarshipApplication.getStudent() == null || scholarshipApplication.getStudent().getId() == null) {
            throw new IllegalArgumentException("Student is required");
        }
        if (scholarshipApplication.getAcademicYear() == null || scholarshipApplication.getAcademicYear().getId() == null) {
            throw new IllegalArgumentException("Academic year is required");
        }
        if (scholarshipApplication.getStatus() != null && scholarshipApplication.getStatus().length() > 50) {
            throw new IllegalArgumentException("Status must not exceed 50 characters");
        }
    }
}