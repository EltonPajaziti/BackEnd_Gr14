package org.backend.service;

import org.backend.model.*;
import org.backend.repository.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Grade createSampleGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setGrade_value(85);

        Student student = new Student();
        student.setId(100L);
        grade.setStudent(student);

        AcademicYear academicYear = new AcademicYear();
        academicYear.setId(200L);
        grade.setAcademicYear(academicYear);

        Professor professor = new Professor();
        professor.setId(300L);
        grade.setProfessor(professor);

        grade.setGradedAt(LocalDate.of(2023, 5, 10));

        Faculty tenant = new Faculty();
        tenant.setId(400L);
        grade.setTenantID(tenant);

        Exam exam = new Exam();
        exam.setId(500L);
        grade.setExam(exam);

        return grade;
    }

    @Test
    void testGetAllGrades() {
        Grade g1 = createSampleGrade();
        Grade g2 = createSampleGrade();
        g2.setId(2L);
        when(gradeRepository.findAll()).thenReturn(List.of(g1, g2));

        List<Grade> grades = gradeService.getAllGrades();

        assertEquals(2, grades.size());
        verify(gradeRepository).findAll();
    }

    @Test
    void testGetGradeById_Found() {
        Grade grade = createSampleGrade();
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        Optional<Grade> result = gradeService.getGradeById(1L);

        assertTrue(result.isPresent());
        assertEquals(grade.getId(), result.get().getId());
        verify(gradeRepository).findById(1L);
    }

    @Test
    void testGetGradeById_NotFound() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Grade> result = gradeService.getGradeById(1L);

        assertTrue(result.isEmpty());
        verify(gradeRepository).findById(1L);
    }

    @Test
    void testCreateGrade() {
        Grade grade = createSampleGrade();

        when(gradeRepository.save(grade)).thenReturn(grade);

        Grade created = gradeService.createGrade(grade);

        assertEquals(85, created.getGrade_value());
        assertEquals(100L, created.getStudent().getId());
        verify(gradeRepository).save(grade);
    }

    @Test
    void testDeleteGrade() {
        doNothing().when(gradeRepository).deleteById(1L);

        gradeService.deleteGrade(1L);

        verify(gradeRepository).deleteById(1L);
    }

    @Test
    void testUpdateGrade_Success() {
        Grade existingGrade = createSampleGrade();
        Grade updateDetails = createSampleGrade();
        updateDetails.setGrade_value(95);
        updateDetails.setGradedAt(LocalDate.of(2023, 6, 15));

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(existingGrade));
        when(gradeRepository.save(existingGrade)).thenReturn(existingGrade);

        Grade updatedGrade = gradeService.updateGrade(1L, updateDetails);

        assertEquals(95, updatedGrade.getGrade_value());
        assertEquals(LocalDate.of(2023, 6, 15), updatedGrade.getGradedAt());
        verify(gradeRepository).findById(1L);
        verify(gradeRepository).save(existingGrade);
    }

    @Test
    void testUpdateGrade_NotFound() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gradeService.updateGrade(1L, new Grade()));

        verify(gradeRepository).findById(1L);
        verify(gradeRepository, never()).save(any());
    }
}
