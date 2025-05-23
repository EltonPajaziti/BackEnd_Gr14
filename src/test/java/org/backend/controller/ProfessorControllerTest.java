package org.backend.controller;

import org.backend.model.Professor;
import org.backend.service.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class ProfessorControllerTest {

    private ProfessorService professorService;
    private ProfessorController professorController;

    @BeforeEach
    public void setup() {
        professorService = Mockito.mock(ProfessorService.class);
        professorController = new ProfessorController();

        try {
            var field = ProfessorController.class.getDeclaredField("professorService");
            field.setAccessible(true);
            field.set(professorController, professorService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetAllProfessors() {
        Professor p1 = new Professor();
        p1.setId(1L);
        Professor p2 = new Professor();
        p2.setId(2L);

        Mockito.when(professorService.getAllProfessors()).thenReturn(List.of(p1, p2));

        List<Professor> result = professorController.getAllProfessors();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetProfessorById_Found() {
        Professor professor = new Professor();
        professor.setId(1L);

        Mockito.when(professorService.getProfessorById(1L)).thenReturn(Optional.of(professor));

        ResponseEntity<Professor> response = professorController.getProfessorById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetProfessorById_NotFound() {
        Mockito.when(professorService.getProfessorById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Professor> response = professorController.getProfessorById(999L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateProfessor() {
        Professor input = new Professor();
        input.setId(null);

        Professor created = new Professor();
        created.setId(1L);

        Mockito.when(professorService.createProfessor(any(Professor.class))).thenReturn(created);

        ResponseEntity<Professor> response = professorController.createProfessor(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdateProfessor() {
        Professor input = new Professor();
        input.setId(1L);

        Professor updated = new Professor();
        updated.setId(1L);

        Mockito.when(professorService.updateProfessor(eq(1L), any(Professor.class))).thenReturn(updated);

        ResponseEntity<Professor> response = professorController.updateProfessor(1L, input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testDeleteProfessor() {
        Mockito.doNothing().when(professorService).deleteProfessor(1L);

        ResponseEntity<Void> response = professorController.deleteProfessor(1L);

        assertEquals(204, response.getStatusCodeValue());
        Mockito.verify(professorService, Mockito.times(1)).deleteProfessor(1L);
    }

    @Test
    public void testCountProfessorsByTenant() {
        Mockito.when(professorService.countProfessorsByTenant(10L)).thenReturn(5L);

        Long count = professorController.countProfessorsByTenant(10L);

        assertEquals(5L, count);
    }
}
