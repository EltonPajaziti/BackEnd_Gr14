package org.backend.service;

import org.backend.dto.FacultyCreateDTO;
import org.backend.model.Faculty;
import org.backend.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFaculties() {
        Faculty f1 = new Faculty();
        Faculty f2 = new Faculty();

        when(facultyRepository.findAll()).thenReturn(List.of(f1, f2));

        List<Faculty> faculties = facultyService.getAllFaculties();

        assertEquals(2, faculties.size());
        verify(facultyRepository).findAll();
    }

    @Test
    void testGetFacultyById_Found() {
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Optional<Faculty> result = facultyService.getFacultyById(1L);

        assertTrue(result.isPresent());
        assertEquals(faculty, result.get());
        verify(facultyRepository).findById(1L);
    }

    @Test
    void testGetFacultyById_NotFound() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Faculty> result = facultyService.getFacultyById(1L);

        assertTrue(result.isEmpty());
        verify(facultyRepository).findById(1L);
    }

    @Test
    void testCreateFaculty() {
        FacultyCreateDTO dto = new FacultyCreateDTO();
        dto.setFacultyId(1L);
        dto.setName("Engineering");
        dto.setEmail("eng@uni.edu");
        dto.setAddress("Main Street");

        Faculty savedFaculty = new Faculty();
        savedFaculty.setId(dto.getFacultyId());
        savedFaculty.setName(dto.getName());
        savedFaculty.setEmail(dto.getEmail());
        savedFaculty.setAddress(dto.getAddress());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(savedFaculty);

        Faculty result = facultyService.createFaculty(dto);

        assertEquals(dto.getFacultyId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getAddress(), result.getAddress());

        verify(facultyRepository).save(any(Faculty.class));
    }

    @Test
    void testUpdateFaculty_Success() {
        Faculty existingFaculty = new Faculty();
        existingFaculty.setName("Old Name");
        existingFaculty.setAddress("Old Address");

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("New Name");
        updatedFaculty.setAddress("New Address");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(existingFaculty));
        when(facultyRepository.save(existingFaculty)).thenReturn(existingFaculty);

        Faculty result = facultyService.updateFaculty(1L, updatedFaculty);

        assertEquals("New Name", result.getName());
        assertEquals("New Address", result.getAddress());

        verify(facultyRepository).findById(1L);
        verify(facultyRepository).save(existingFaculty);
    }

    @Test
    void testUpdateFaculty_NotFound() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> facultyService.updateFaculty(1L, new Faculty()));

        verify(facultyRepository).findById(1L);
        verify(facultyRepository, never()).save(any());
    }

    @Test
    void testDeleteFaculty() {
        doNothing().when(facultyRepository).deleteById(1L);

        facultyService.deleteFaculty(1L);

        verify(facultyRepository).deleteById(1L);
    }
}
