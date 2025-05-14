package org.backend.controller;

import org.backend.dto.UserCreateDTO;
import org.backend.model.Users;
import org.backend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow();
    }

//    @PostMapping
//    public Users createUser(@RequestBody Users user) {
//        return userService.createUser(user);
//    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/create")
    public Users createUser(@RequestBody UserCreateDTO dto,
                            @RequestParam("tenantId") Long tenantId) {
        return userService.createUser(dto, tenantId);
    }

}
