package org.backend.service;

import org.backend.model.Professor;
import org.backend.model.Users;
import org.backend.model.Department;
import org.backend.model.Faculty;
import org.backend.repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProfessors() {
        Professor p1 = new Professor();
        Professor p2 = new Professor();
        when(professorRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Professor> professors = professorService.getAllProfessors();

        assertEquals(2, professors.size());
        verify(professorRepository).findAll();
    }

    @Test
    void testGetProfessorById_Found() {
        Professor professor = new Professor();
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        Optional<Professor> result = professorService.getProfessorById(1L);

        assertTrue(result.isPresent());
        assertEquals(professor, result.get());
        verify(professorRepository).findById(1L);
    }

    @Test
    void testGetProfessorById_NotFound() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Professor> result = professorService.getProfessorById(1L);

        assertTrue(result.isEmpty());
        verify(professorRepository).findById(1L);
    }

    @Test
    void testCreateProfessor() {
        Professor professor = new Professor();
        when(professorRepository.save(professor)).thenReturn(professor);

        Professor result = professorService.createProfessor(professor);

        assertEquals(professor, result);
        verify(professorRepository).save(professor);
    }

    @Test
    void testUpdateProfessor_Success() {
        Professor existingProfessor = new Professor();
        existingProfessor.setAcademicTitle("Old Title");
        existingProfessor.setHiredDate(LocalDate.of(2020, 1, 1));
        existingProfessor.setUser(new Users());
        existingProfessor.setDepartment(new Department());
        existingProfessor.setTenantID(new Faculty());

        Professor updatedProfessor = new Professor();
        updatedProfessor.setAcademicTitle("New Title");
        updatedProfessor.setHiredDate(LocalDate.of(2025, 5, 1));
        updatedProfessor.setUser(new Users());
        updatedProfessor.setDepartment(new Department());
        updatedProfessor.setTenantID(new Faculty());

        when(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor));
        when(professorRepository.save(existingProfessor)).thenReturn(existingProfessor);

        Professor result = professorService.updateProfessor(1L, updatedProfessor);

        assertEquals("New Title", result.getAcademicTitle());
        assertEquals(LocalDate.of(2025, 5, 1), result.getHiredDate());
        assertEquals(updatedProfessor.getUser(), result.getUser());
        assertEquals(updatedProfessor.getDepartment(), result.getDepartment());
        assertEquals(updatedProfessor.getTenantID(), result.getTenantID());

        verify(professorRepository).findById(1L);
        verify(professorRepository).save(existingProfessor);
    }

    @Test
    void testUpdateProfessor_NotFound() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            professorService.updateProfessor(1L, new Professor());
        });

        assertEquals("Profesori nuk u gjet", thrown.getMessage());

        verify(professorRepository).findById(1L);
        verify(professorRepository, never()).save(any());
    }

    @Test
    void testCountProfessorsByTenant() {
        when(professorRepository.countByTenantID_Id(5L)).thenReturn(3L);

        Long count = professorService.countProfessorsByTenant(5L);

        assertEquals(3L, count);
        verify(professorRepository).countByTenantID_Id(5L);
    }

    @Test
    void testDeleteProfessor_Success() {
        when(professorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(professorRepository).deleteById(1L);

        professorService.deleteProfessor(1L);

        verify(professorRepository).existsById(1L);
        verify(professorRepository).deleteById(1L);
    }

    @Test
    void testDeleteProfessor_NotFound() {
        when(professorRepository.existsById(1L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            professorService.deleteProfessor(1L);
        });

        assertEquals("Profesori nuk ekziston", thrown.getMessage());

        verify(professorRepository).existsById(1L);
        verify(professorRepository, never()).deleteById(anyLong());
    }
}
