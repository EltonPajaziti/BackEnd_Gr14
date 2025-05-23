package org.backend.service;

import org.backend.dto.UserCreateDTO;
import org.backend.enums.Gender;
import org.backend.model.*;
import org.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProgramRepository programRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Users createUser(UserCreateDTO dto, Long tenantId) {
        Faculty tenant = facultyRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant nuk ekziston"));

        UserRole role = roleRepository.findByName(dto.getRoleName().toUpperCase());
        if (role == null) {
            throw new IllegalArgumentException("Roli '" + dto.getRoleName() + "' nuk ekziston.");
        }

        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        user.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        user.setNationalId(dto.getNationalId());
        user.setProfilePicture(dto.getProfilePicture());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setTenantID(tenant);
        user.setRole(role);

        Users savedUser = userRepository.save(user);

        if (role.getName().equalsIgnoreCase("STUDENT")) {
            Student student = new Student();
            student.setUser(savedUser);
            student.setTenantID(tenant);
            student.setEnrollmentDate(LocalDate.now());

            Program program = programRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Program default nuk u gjet"));
            student.setProgram(program);

            studentRepository.save(student);
        }

        if (role.getName().equalsIgnoreCase("PROFESSOR")) {
            Professor professor = new Professor();
            professor.setUser(savedUser);
            professor.setTenantID(tenant);
            professor.setHiredDate(LocalDate.now());
            professor.setAcademicTitle("Prof. Dr.");
            professorRepository.save(professor);
        }

        return savedUser;
    }

    public Users updateUser(Long id, Users updatedUser) {
        Users existingUser = userRepository.findById(id).orElseThrow();

        if (!passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        } else {
            updatedUser.setPassword(existingUser.getPassword());
        }

        if (updatedUser.getRole() != null && updatedUser.getRole().getId() == null) {
            if (!List.of("STUDENT", "ADMIN", "PROFESSOR").contains(updatedUser.getRole().getName())) {
                throw new IllegalArgumentException("Invalid role name: " + updatedUser.getRole().getName());
            }
            UserRole role = roleRepository.findByName(updatedUser.getRole().getName());
            if (role == null) {
                throw new IllegalArgumentException("Role '" + updatedUser.getRole().getName() + "' doesn't exist.");
            }
            updatedUser.setRole(role);
        }

        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
