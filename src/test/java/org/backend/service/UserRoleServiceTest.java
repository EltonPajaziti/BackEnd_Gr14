package org.backend.service;

import org.backend.model.UserRole;
import org.backend.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceTest {

    @Mock
    private UserRoleRepository roleRepository;

    @InjectMocks
    private UserRoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        UserRole role1 = new UserRole("ADMIN");
        UserRole role2 = new UserRole("STUDENT");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<UserRole> roles = roleService.getAllRoles();

        assertEquals(2, roles.size());
        assertEquals("ADMIN", roles.get(0).getName());
        assertEquals("STUDENT", roles.get(1).getName());
    }

    @Test
    void testGetById_Found() {
        UserRole role = new UserRole("ADMIN");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<UserRole> result = roleService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
    }

    @Test
    void testGetById_NotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserRole> result = roleService.getById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateRole_Success() {
        UserRole role = new UserRole("PROFESSOR");

        when(roleRepository.findByName("PROFESSOR")).thenReturn(null);
        when(roleRepository.save(role)).thenReturn(role);

        UserRole savedRole = roleService.createRole(role);

        assertNotNull(savedRole);
        assertEquals("PROFESSOR", savedRole.getName());
    }

    @Test
    void testCreateRole_DuplicateName_ThrowsException() {
        UserRole existingRole = new UserRole("ADMIN");

        when(roleRepository.findByName("ADMIN")).thenReturn(existingRole);

        UserRole newRole = new UserRole("ADMIN");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roleService.createRole(newRole);
        });

        assertEquals("Roli 'ADMIN' ekziston tashmë.", exception.getMessage());
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleRepository).deleteById(1L);

        roleService.deleteRole(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }
}
