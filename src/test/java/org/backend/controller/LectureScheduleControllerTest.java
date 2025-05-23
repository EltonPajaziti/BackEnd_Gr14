package org.backend.controller;

import org.backend.dto.LectureScheduleDTO;
import org.backend.model.LectureSchedule;
import org.backend.service.LectureScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class LectureScheduleControllerTest {

    private LectureScheduleService lectureScheduleService;
    private LectureScheduleController lectureScheduleController;

    @BeforeEach
    public void setup() {
        lectureScheduleService = Mockito.mock(LectureScheduleService.class);
        lectureScheduleController = new LectureScheduleController(lectureScheduleService);
    }

    @Test
    public void testGetAll() {
        LectureSchedule ls1 = new LectureSchedule();
        ls1.setId(1L);
        LectureSchedule ls2 = new LectureSchedule();
        ls2.setId(2L);

        Mockito.when(lectureScheduleService.getAll()).thenReturn(List.of(ls1, ls2));

        List<LectureSchedule> result = lectureScheduleController.getAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetById_Found() {
        LectureSchedule ls = new LectureSchedule();
        ls.setId(1L);

        Mockito.when(lectureScheduleService.getById(1L)).thenReturn(Optional.of(ls));

        LectureSchedule result = lectureScheduleController.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetById_NotFound() {
        Mockito.when(lectureScheduleService.getById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lectureScheduleController.getById(999L);
        });

        assertEquals("Lecture schedule not found", exception.getMessage());
    }

    @Test
    public void testCreate_Valid() {
        LectureSchedule input = new LectureSchedule();
        input.setId(null);

        LectureSchedule created = new LectureSchedule();
        created.setId(1L);

        Mockito.when(lectureScheduleService.create(any(LectureSchedule.class))).thenReturn(created);

        // Mock BindingResult pa error
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<LectureSchedule> response = lectureScheduleController.create(input, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testCreate_Invalid() {
        LectureSchedule input = new LectureSchedule();

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<LectureSchedule> response = lectureScheduleController.create(input, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdate_Valid() {
        LectureSchedule input = new LectureSchedule();
        input.setId(1L);

        LectureSchedule updated = new LectureSchedule();
        updated.setId(1L);

        Mockito.when(lectureScheduleService.update(eq(1L), any(LectureSchedule.class))).thenReturn(updated);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<LectureSchedule> response = lectureScheduleController.update(1L, input, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdate_Invalid() {
        LectureSchedule input = new LectureSchedule();

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<LectureSchedule> response = lectureScheduleController.update(1L, input, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdate_NotFound() {
        LectureSchedule input = new LectureSchedule();

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        Mockito.when(lectureScheduleService.update(eq(1L), any(LectureSchedule.class)))
                .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<LectureSchedule> response = lectureScheduleController.update(1L, input, bindingResult);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testDelete_Success() {
        Mockito.doNothing().when(lectureScheduleService).delete(1L);

        ResponseEntity<Void> response = lectureScheduleController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testDelete_NotFound() {
        Mockito.doThrow(new RuntimeException("Not found")).when(lectureScheduleService).delete(999L);

        ResponseEntity<Void> response = lectureScheduleController.delete(999L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetByTenant() {
        LectureSchedule ls = new LectureSchedule();
        ls.setId(1L);

        Mockito.when(lectureScheduleService.getByTenantId(10L)).thenReturn(List.of(ls));

        List<LectureSchedule> result = lectureScheduleController.getByTenant(10L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetByCourseProfessor() {
        LectureSchedule ls = new LectureSchedule();
        ls.setId(1L);

        Mockito.when(lectureScheduleService.getByCourseProfessorId(20L)).thenReturn(List.of(ls));

        List<LectureSchedule> result = lectureScheduleController.getByCourseProfessor(20L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetByProgram() {
        LectureSchedule ls = new LectureSchedule();
        ls.setId(1L);

        Mockito.when(lectureScheduleService.getByProgramId(30L)).thenReturn(List.of(ls));

        List<LectureSchedule> result = lectureScheduleController.getByProgram(30L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetStudentSchedule() {
        LectureScheduleDTO dto = new LectureScheduleDTO();

        Mockito.when(lectureScheduleService.getStudentSchedule(1L, 100L)).thenReturn(List.of(dto));

        ResponseEntity<List<LectureScheduleDTO>> response = lectureScheduleController.getStudentSchedule(1L, 100L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
}
