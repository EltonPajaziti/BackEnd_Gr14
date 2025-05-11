package org.backend.service;

import org.backend.model.UserRole;
import org.backend.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository roleRepository;

    public List<UserRole> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<UserRole> getById(Long id) {
        return roleRepository.findById(id);
    }

    public UserRole createRole(UserRole role) {
        // Validim për duplikim sipas emrit
        if (roleRepository.findByName(role.getName()) != null) {
            throw new IllegalArgumentException("Roli '" + role.getName() + "' ekziston tashmë.");
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
