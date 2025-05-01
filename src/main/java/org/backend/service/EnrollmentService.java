package org.backend.service;

import org.backend.model.Enrollment;
import org.backend.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow();

        //ERROR DUHET ME E PERMIRESU
<<<<<<< HEAD
                enrollment.setStudent(updatedEnrollment.getStudent());
        enrollment.setCourse(updatedEnrollment.getCourse());
        enrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        enrollment.setAcademicYear(updatedEnrollment.getAcademicYear());

        enrollment.setTenantID(updatedEnrollment.getTenantID());
=======
        //        enrollment.setStudentId(updatedEnrollment.getStudentId());
//        enrollment.setCourseId(updatedEnrollment.getCourseId());
        enrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        enrollment.setAcademicYear(updatedEnrollment.getAcademicYear());
        //ERROR DUHET ME E PERMIRESU
//        enrollment.setTenantId(updatedEnrollment.getTenantId());
>>>>>>> main
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
