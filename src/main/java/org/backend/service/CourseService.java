package org.backend.service;

import org.backend.model.Course;
import org.backend.model.Enrollment;
import org.backend.model.Student;
import org.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.backend.repository.StudentRepository;
import org.backend.repository.EnrollmentRepository;
import java.util.Set;
import java.util.HashSet;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id){
        return courseRepository.findById(id);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findCourseByName(name);
    }

    public Course getCourseByCode(String code) {
        return courseRepository.findCourseByCode(code);
    }

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setName(updatedCourse.getName());
        course.setCode(updatedCourse.getCode());
        //ERROR DUHET ME E PERMIRESU
//        course.setTenantId(updatedCourse.getTenantId());
        course.setProgram(updatedCourse.getProgram());

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Long countCourseByTenant(Long tenantId) {
        return courseRepository.countByTenantID_Id(tenantId);
    }
    public List<Course> getCoursesByProgramId(Long programId) {
        return courseRepository.findByProgramId(programId);
    }

//    public List<Course> getAvailableCoursesForStudent(Long studentId, Short semester) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        List<Course> allCourses = courseRepository.findByProgram_IdAndTenantID_IdAndSemester(
//                student.getProgram().getId(),
//                student.getTenantID().getId(),
//                semester
//        );
//
//        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);
//        Set<Long> enrolledCourseIds = enrollments.stream()
//                .map(e -> e.getCourse().getId())
//                .collect(Collectors.toSet());
//
//        return allCourses.stream()
//                .filter(course -> !enrolledCourseIds.contains(course.getId()))
//                .toList();
//    }

//    public List<Short> getAvailableSemesters(Long tenantId) {
//        List<Course> courses = courseRepository.findDistinctByTenantID_IdOrderBySemesterAsc(tenantId);
//        return courses.stream()
//                .map(Course::getSemester)
//                .distinct()
//                .sorted()
//                .collect(Collectors.toList());
//    }


}
