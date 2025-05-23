package org.backend.controller;

import org.backend.dto.UserCreateDTO;
import org.backend.model.Users;
import org.backend.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController)
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Users user1 = new Users();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");

        Users user2 = new Users();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");

        when(usersService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].email").value("jane.smith@example.com"));
    }

    @Test
    public void testGetUserById() throws Exception {
        Users user = new Users();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(usersService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateUser() throws Exception {
        Users user = new Users();
        user.setId(1L);
        user.setFirstName("UpdatedJohn");
        user.setLastName("Doe");
        user.setEmail("updated.john.doe@example.com");

        when(usersService.updateUser(anyLong(), any(Users.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"UpdatedJohn\",\"lastName\":\"Doe\",\"email\":\"updated.john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("UpdatedJohn"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("updated.john.doe@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(usersService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateUser() throws Exception {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setFirstName("NewUser");
        dto.setLastName("New");
        dto.setEmail("new.user@example.com");
        dto.setPhone("123456789");
        dto.setAddress("123 Main St");
        dto.setGender("MALE");
        dto.setDateOfBirth("1990-01-01");
        dto.setNationalId("1234567890");
        dto.setProfilePicture("profile.jpg");
        dto.setRoleName("STUDENT");
        dto.setPassword("password123");

        Users user = new Users();
        user.setId(1L);
        user.setFirstName("NewUser");
        user.setLastName("New");
        user.setEmail("new.user@example.com");

        when(usersService.createUser(any(UserCreateDTO.class), eq(1L))).thenReturn(user);

        mockMvc.perform(post("/api/users/create")
                        .param("tenantId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"NewUser\",\"lastName\":\"New\",\"email\":\"new.user@example.com\",\"phone\":\"123456789\",\"address\":\"123 Main St\",\"gender\":\"MALE\",\"dateOfBirth\":\"1990-01-01\",\"nationalId\":\"1234567890\",\"profilePicture\":\"profile.jpg\",\"roleName\":\"STUDENT\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("NewUser"))
                .andExpect(jsonPath("$.lastName").value("New"))
                .andExpect(jsonPath("$.email").value("new.user@example.com"));
    }
}