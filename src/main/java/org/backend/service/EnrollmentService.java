package org.backend.service;

import org.backend.dto.CourseDTO;
import org.backend.dto.EnrollmentRequestDTO;
import org.backend.dto.RegisteredCourseDTO;
import org.backend.model.*;
import org.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private CourseProfessorRepository courseProfessorRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsersRepository userRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudent_Id(studentId);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

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
        enrollment.setAcademicYear(null);

        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment nuk u gjet për ID: " + id));

        enrollment.setStudent(updatedEnrollment.getStudent());
        enrollment.setCourse(updatedEnrollment.getCourse());
        enrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        enrollment.setAcademicYear(null);
        enrollment.setTenantID(updatedEnrollment.getTenantID());

        return enrollmentRepository.save(enrollment);
    }

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

            String professorName = course.getCourseProfessors() != null && !course.getCourseProfessors().isEmpty()
                    ? course.getCourseProfessors().get(0).getProfessor().getUser().getFirstName() + " " +
                    course.getCourseProfessors().get(0).getProfessor().getUser().getLastName()
                    : "N/A";

            dto.setProfessorName(professorName);
            return dto;
        }).toList();
    }

    public List<CourseDTO> getRegisteredCoursesBySemester(Long studentId, int semester) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);
        List<CourseDTO> courses = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();

            if (course.getSemester() != null && course.getSemester() == semester) {
                CourseDTO dto = new CourseDTO();
                dto.setId(course.getId());
                dto.setName(course.getName());
                dto.setCode(course.getCode());
                dto.setCredits(course.getCredits());

                List<Course_Professor> cpList = courseProfessorRepository.findByCourseId(course.getId());
                if (!cpList.isEmpty()) {
                    Professor professor = cpList.get(0).getProfessor();
                    if (professor != null && professor.getUser() != null) {
                        dto.setProfessorName(professor.getUser().getFirstName() + " " + professor.getUser().getLastName());
                    }
                }

                courses.add(dto);
            }
        }

        return courses;
    }


}
