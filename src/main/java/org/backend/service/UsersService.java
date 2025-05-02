package org.backend.service;

import org.backend.model.Users;
import org.backend.repository.UsersRepository;
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
    private PasswordEncoder passwordEncoder;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        // Hashimi i fjalëkalimit para ruajtjes
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Users updateUser(Long id, Users updatedUser) {
        Users existingUser = userRepository.findById(id).orElseThrow();

        // Nëse fjalëkalimi i ri nuk përputhet me hash-in ekzistues, e hash-ojmë
        if (!passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        } else {
            updatedUser.setPassword(existingUser.getPassword()); // ruaj hash-in ekzistues
        }

        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
