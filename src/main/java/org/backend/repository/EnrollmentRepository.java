package org.backend.repository;

import org.backend.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Merr të gjitha regjistrimet për një student të caktuar
    List<Enrollment> findByStudent_Id(Long studentId);

    // Kontrollo nëse studenti është regjistruar tashmë në një kurs të caktuar
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);

    // Merr të gjitha regjistrimet për një student dhe vit akademik të caktuar
    List<Enrollment> findByStudent_IdAndAcademicYear_Id(Long studentId, Long academicYearId);

    // Merr të gjitha regjistrimet për një student dhe tenant të caktuar
    List<Enrollment> findByStudent_IdAndTenantID_Id(Long studentId, Long tenantId);
}
