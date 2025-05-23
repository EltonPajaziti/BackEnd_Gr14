package org.backend.service;

import org.backend.dto.CourseDTO;
import org.backend.mapper.CourseMapper;
import org.backend.model.*;
import org.backend.model.Course_Professor;
import org.backend.repository.CourseProfessorRepository;
import org.backend.repository.CourseRepository;
import org.backend.repository.EnrollmentRepository;
import org.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private CourseProfessorRepository courseProfessorRepo;

    @Autowired
    private CourseMapper courseMapper;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));


        if (updatedStudent.getUser() != null && updatedStudent.getUser().getId() != null) {
            Users user = student.getUser();
            user.setFirstName(updatedStudent.getUser().getFirstName());
            user.setLastName(updatedStudent.getUser().getLastName());
            user.setEmail(updatedStudent.getUser().getEmail());
        }

        student.setEnrollmentDate(updatedStudent.getEnrollmentDate());

        if (updatedStudent.getProgram() != null && updatedStudent.getProgram().getId() != null) {
            student.setProgram(updatedStudent.getProgram());
        }

        if (updatedStudent.getTenantID() != null && updatedStudent.getTenantID().getId() != null) {
            student.setTenantID(updatedStudent.getTenantID());
        }

        return studentRepository.save(student);
    }

    public List<Student> getStudentsByTenant(Long tenantId) {
        return studentRepository.findByTenantID_Id(tenantId);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public long countStudentsByTenant(Long tenantId) {
        return studentRepository.countByTenantID_Id(tenantId);
    }

    public Optional<Student> getStudentByUserId(Long userId) {
        return studentRepository.findByUser_Id(userId);
    }

    //  Kjo është metoda e re që do thërritet nga Controller-i për të marrë kurset
    public List<CourseDTO> getAvailableCourses(Long studentId, Short semester) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        int year = student.getYearOfStudy();
        if ((year == 1 && semester > 2) ||
                (year == 2 && semester > 4) ||
                (year == 3 && semester > 6) ||
                (year == 4 && semester > 8)) {
            return List.of();
        }

        Long programId = student.getProgram().getId();
        Long tenantId = student.getTenantID().getId();

        List<Course> allCourses = courseRepo
                .findByProgram_IdAndTenantID_IdAndSemester(programId, tenantId, semester);

        List<Long> enrolledCourseIds = enrollmentRepo.findByStudent_Id(studentId)
                .stream().map(e -> e.getCourse().getId()).toList();


        return allCourses.stream()
                .filter(c -> !enrolledCourseIds.contains(c.getId()))
                .map(course -> {
                    List<Course_Professor> cpList = courseProfessorRepo.findByCourseId(course.getId());
                    String profName = cpList.isEmpty() ? "—" :
                            cpList.get(0).getProfessor().getUser().getFirstName() + " " +
                                    cpList.get(0).getProfessor().getUser().getLastName();
                    return courseMapper.toDTO(course, profName);
                })
                .toList();
    }
}
