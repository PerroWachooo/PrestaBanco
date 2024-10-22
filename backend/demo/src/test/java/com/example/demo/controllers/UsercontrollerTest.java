package com.example.demo.controllers;
import com.example.demo.controller.UserController;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsercontrollerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testListUsersReturnsNonEmptyList() throws Exception {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5, 5000, 1000, 10, 2000));
        when(userService.getUsers()).thenReturn(new ArrayList<>(users));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetEmployeeByIdSuccess() throws Exception {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5, 5000, 1000, 10, 2000);
        when(userService.getUserById(1L)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetEmployeeByIdNotFound() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5, 5000, 1000, 10, 2000);
        when(userService.registerUser(any(UserEntity.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rut\":\"12345678-9\",\"password\":\"password\",\"mail\":\"test@mail.com\",\"name\":\"John Doe\",\"age\":30}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(userService, times(1)).registerUser(any(UserEntity.class));
    }

    @Test
    void testRegisterUserInvalidAge() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rut\":\"12345678-9\",\"password\":\"password\",\"mail\":\"test@mail.com\",\"name\":\"John Doe\",\"age\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        // Arrange
        doThrow(new Exception("User not found")).when(userService).deleteUser(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
