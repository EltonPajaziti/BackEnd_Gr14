package org.backend.service;

import org.backend.model.Student;
import org.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

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
        Student student = studentRepository.findById(id).orElseThrow();
// ERRORA, DUHET ME I PERMIRESU
//        student.setUserId(updatedStudent.getUserId());
//        student.setProgramId(updatedStudent.getProgramId());
//        student.setTenantId(updatedStudent.getTenantId());
        student.setEnrollmentDate(updatedStudent.getEnrollmentDate());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
