package org.backend.service;

import org.backend.model.AcademicYear;
import org.backend.repository.AcademicYearRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcademicYearServiceTest {

    @Mock
    private AcademicYearRepository academicYearRepository;

    @InjectMocks
    private AcademicYearService academicYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private AcademicYear createSampleAcademicYear() {
        AcademicYear ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024/2025");
        ay.setStartDate(LocalDate.of(2024, 9, 1));
        ay.setEndDate(LocalDate.of(2025, 6, 30));
        ay.setIsActive(true);
        // tenant nuk e kemi përfshirë, mund ta shtosh nëse e ke modelin
        return ay;
    }

    @Test
    void testGetAll() {
        AcademicYear ay1 = createSampleAcademicYear();
        AcademicYear ay2 = createSampleAcademicYear();
        ay2.setId(2L);
        ay2.setName("2025/2026");

        when(academicYearRepository.findAll()).thenReturn(List.of(ay1, ay2));

        List<AcademicYear> result = academicYearService.getAll();

        assertEquals(2, result.size());
        verify(academicYearRepository).findAll();
    }

    @Test
    void testGetById_Found() {
        AcademicYear ay = createSampleAcademicYear();
        when(academicYearRepository.findById(1L)).thenReturn(Optional.of(ay));

        Optional<AcademicYear> result = academicYearService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("2024/2025", result.get().getName());
        verify(academicYearRepository).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(academicYearRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AcademicYear> result = academicYearService.getById(1L);

        assertTrue(result.isEmpty());
        verify(academicYearRepository).findById(1L);
    }

    @Test
    void testCreate() {
        AcademicYear ay = createSampleAcademicYear();
        when(academicYearRepository.save(ay)).thenReturn(ay);

        AcademicYear created = academicYearService.create(ay);

        assertEquals("2024/2025", created.getName());
        verify(academicYearRepository).save(ay);
    }

    @Test
    void testUpdate_Success() {
        AcademicYear existing = createSampleAcademicYear();
        AcademicYear updated = createSampleAcademicYear();
        updated.setName("2025/2026");
        updated.setIsActive(false);

        when(academicYearRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(academicYearRepository.save(existing)).thenReturn(existing);

        AcademicYear result = academicYearService.update(1L, updated);

        assertEquals("2025/2026", result.getName());
        assertFalse(result.getIsActive());
        verify(academicYearRepository).findById(1L);
        verify(academicYearRepository).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        when(academicYearRepository.findById(1L)).thenReturn(Optional.empty());

        AcademicYear updated = createSampleAcademicYear();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            academicYearService.update(1L, updated);
        });

        assertEquals("Academic Year not found", ex.getMessage());
        verify(academicYearRepository).findById(1L);
        verify(academicYearRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(academicYearRepository).deleteById(1L);

        academicYearService.delete(1L);

        verify(academicYearRepository).deleteById(1L);
    }

    @Test
    void testGetByTenantId() {
        AcademicYear ay = createSampleAcademicYear();
        when(academicYearRepository.findByTenantID_Id(10L)).thenReturn(List.of(ay));

        List<AcademicYear> result = academicYearService.getByTenantId(10L);

        assertEquals(1, result.size());
        verify(academicYearRepository).findByTenantID_Id(10L);
    }

    @Test
    void testGetActiveYears() {
        AcademicYear ay = createSampleAcademicYear();
        when(academicYearRepository.findByIsActiveTrue()).thenReturn(List.of(ay));

        List<AcademicYear> result = academicYearService.getActiveYears();

        assertEquals(1, result.size());
        verify(academicYearRepository).findByIsActiveTrue();
    }
}
