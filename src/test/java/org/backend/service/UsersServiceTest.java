package org.backend.service;

import org.backend.dto.UserCreateDTO;
import org.backend.enums.Gender;
import org.backend.model.*;
import org.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private UserRoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProgramRepository programRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_WithStudentRole_Success() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setEmail("test@example.com");
        dto.setPhone("123456789");
        dto.setAddress("Address");
        dto.setGender("MALE");
        dto.setDateOfBirth("2000-01-01");
        dto.setNationalId("1234567890");
        dto.setProfilePicture("profile.jpg");
        dto.setPassword("password");
        dto.setRoleName("STUDENT");

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        UserRole role = new UserRole();
        role.setId(1L);
        role.setName("STUDENT");

        Users user = new Users();
        user.setId(1L);

        Program program = new Program();
        program.setId(1L);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(roleRepository.findByName("STUDENT")).thenReturn(role);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));

        Users result = usersService.createUser(dto, 1L);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testGetUserById_Found() {
        Users user = new Users();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<Users> result = usersService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testUpdateUser_EncodesNewPassword() {
        Users existingUser = new Users();
        existingUser.setPassword("oldEncoded");

        Users updateData = new Users();
        updateData.setPassword("newPassword");
        UserRole role = new UserRole();
        role.setName("STUDENT");
        updateData.setRole(role);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("newPassword", "oldEncoded")).thenReturn(false);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(roleRepository.findByName("STUDENT")).thenReturn(role);
        when(userRepository.save(any(Users.class))).thenReturn(updateData);

        Users result = usersService.updateUser(1L, updateData);

        assertEquals("encodedNewPassword", result.getPassword());
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteById(1L);
        usersService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
