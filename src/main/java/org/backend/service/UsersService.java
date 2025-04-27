package org.backend.service;

import org.backend.model.Users;
import org.backend.repository.UsersRepository;
import org.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public Users updateUser(Long id, Users updatedUser) {
        Users user = userRepository.findById(id).orElseThrow();
        // vendos përditësimin e fushave nëse do
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
