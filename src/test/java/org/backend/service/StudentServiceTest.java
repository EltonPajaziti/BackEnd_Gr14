package org.backend.service;

import org.backend.model.*;
import org.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        Student s1 = new Student();
        Student s2 = new Student();
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Student> students = studentService.getAllStudents();

        assertEquals(2, students.size());
        verify(studentRepository).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        Student student = new Student();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals(student, result.get());
        verify(studentRepository).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(1L);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);

        assertEquals(student, result);
        verify(studentRepository).save(student);
    }

    @Test
    void testUpdateStudent_Success() {
        Student existingStudent = new Student();
        existingStudent.setUser(new Users());
        existingStudent.setProgram(new Program());
        existingStudent.setTenantID(new Faculty());
        existingStudent.setEnrollmentDate(LocalDate.now());

        Student updatedStudent = new Student();
        updatedStudent.setUser(new Users());
        updatedStudent.setProgram(new Program());
        updatedStudent.setTenantID(new Faculty());
        updatedStudent.setEnrollmentDate(LocalDate.of(2025, 1, 1));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student result = studentService.updateStudent(1L, updatedStudent);

        assertEquals(updatedStudent.getUser(), result.getUser());
        assertEquals(updatedStudent.getProgram(), result.getProgram());
        assertEquals(updatedStudent.getTenantID(), result.getTenantID());
        assertEquals(updatedStudent.getEnrollmentDate(), result.getEnrollmentDate());

        verify(studentRepository).findById(1L);
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(1L, new Student());
        });

        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }
}
