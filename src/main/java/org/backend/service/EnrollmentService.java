package org.backend.service;

import org.backend.dto.EnrollmentRequestDTO;
import org.backend.model.Course;
import org.backend.model.Enrollment;
import org.backend.model.Faculty;
import org.backend.model.Student;
import org.backend.repository.CourseRepository;
import org.backend.repository.EnrollmentRepository;
import org.backend.repository.FacultyRepository;
import org.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.backend.dto.RegisteredCourseDTO;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    // GET
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudent_Id(studentId);
    }

    // CREATE (nëse ndonjë controller e përdor ende këtë)
    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    // ENROLL student using DTO pa academicYear
    public Enrollment enrollStudent(EnrollmentRequestDTO dto) {
        if (enrollmentRepository.existsByStudent_IdAndCourse_Id(dto.getStudentId(), dto.getCourseId())) {
            throw new RuntimeException("Studenti është regjistruar tashmë në këtë kurs.");
        }

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Studenti nuk u gjet."));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Kursi nuk u gjet."));

        Faculty tenant = facultyRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant ID nuk u gjet."));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setTenantID(tenant);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setAcademicYear(null); // nuk përdoret

        return enrollmentRepository.save(enrollment);
    }

    // UPDATE
    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment nuk u gjet për ID: " + id));

        enrollment.setStudent(updatedEnrollment.getStudent());
        enrollment.setCourse(updatedEnrollment.getCourse());
        enrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        enrollment.setAcademicYear(null); // gjithashtu e lëmë null
        enrollment.setTenantID(updatedEnrollment.getTenantID());

        return enrollmentRepository.save(enrollment);
    }

    // DELETE
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }




    public List<RegisteredCourseDTO> getRegisteredCoursesForStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);

        return enrollments.stream().map(enrollment -> {
            RegisteredCourseDTO dto = new RegisteredCourseDTO();
            Course course = enrollment.getCourse();

            dto.setCourseId(course.getId());
            dto.setCode(course.getCode());
            dto.setName(course.getName());
            dto.setCredits(course.getCredits());

            // Marrim profesorin nga course.getCourseProfessors() (nëse ekziston)
            String professorName = course.getCourseProfessors() != null && !course.getCourseProfessors().isEmpty()
                    ? course.getCourseProfessors().get(0).getProfessor().getUser().getFirstName() + " " +
                    course.getCourseProfessors().get(0).getProfessor().getUser().getLastName()
                    : "N/A";

            dto.setProfessorName(professorName);
            return dto;
        }).toList();
    }
}
