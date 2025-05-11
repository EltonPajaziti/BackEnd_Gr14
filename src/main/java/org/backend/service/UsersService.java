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

        //  VALIDIM: Kontrollo nëse emri i rolit është i lejuar
        if (user.getRole() != null && user.getRole().getId() == null) {
            if (!List.of("STUDENT", "ADMIN", "PROFESSOR").contains(user.getRole().getName())) {
                throw new IllegalArgumentException("Invalid role name: " + user.getRole().getName());
            }

            UserRole role = roleRepository.findByName(user.getRole().getName());
            if (role == null) {
                throw new IllegalArgumentException("Role '" + user.getRole().getName() + "' doesn't exist.");
            }
            user.setRole(role);
        }

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
