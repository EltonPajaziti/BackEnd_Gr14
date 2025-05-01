package org.backend.service;

import org.backend.model.AcademicYear;
import org.backend.repository.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    public List<AcademicYear> getAll() {
        return academicYearRepository.findAll();
    }

    public Optional<AcademicYear> getById(Long id) {
        return academicYearRepository.findById(id);
    }

    public AcademicYear create(AcademicYear academicYear) {
        return academicYearRepository.save(academicYear);
    }

    public AcademicYear update(Long id, AcademicYear updated) {
        AcademicYear ay = academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic Year not found"));

        ay.setName(updated.getName());
        ay.setStartDate(updated.getStartDate());
        ay.setEndDate(updated.getEndDate());
        ay.setIsActive(updated.getIsActive());
//        ay.setTenant(updated.getTenant());
        // duhet nje permiresim!!!

        return academicYearRepository.save(ay);
    }

    public void delete(Long id) {
        academicYearRepository.deleteById(id);
    }

    public List<AcademicYear> getByTenantId(Long tenantId) {
        return academicYearRepository.findByTenantID_Id(tenantId);
    }

    public List<AcademicYear> getActiveYears() {
        return academicYearRepository.findByIsActiveTrue();
    }
}

