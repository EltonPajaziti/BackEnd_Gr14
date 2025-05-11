package org.backend.service;

import org.backend.model.Users;
import org.backend.model.UserRole;
import org.backend.repository.UsersRepository;
import org.backend.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().getName() == null) {
            throw new IllegalArgumentException("Duhet të dërgosh emrin e rolit.");
        }

        String roleName = user.getRole().getName().toUpperCase(); // ADMIN, PROFESSOR, STUDENT

        if (!List.of("STUDENT", "ADMIN", "PROFESSOR").contains(roleName)) {
            throw new IllegalArgumentException("Roli i panjohur: " + roleName);
        }

        UserRole role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Roli '" + roleName + "' nuk ekziston në databazë.");
        }

        user.setRole(role); // vendos objektin korrekt të entitetit

        return userRepository.save(user);
    }



    public Users updateUser(Long id, Users updatedUser) {
        Users existingUser = userRepository.findById(id).orElseThrow();

        // Hasho fjalëkalimin vetëm nëse është ndryshuar
        if (!passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        } else {
            updatedUser.setPassword(existingUser.getPassword());
        }

        // Përditëso rolin nëse vjen me emër pa ID
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
