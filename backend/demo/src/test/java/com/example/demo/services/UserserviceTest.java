package com.example.demo.services;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

class UserserviceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsersReturnsNonEmptyList() {
        // Arrange
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5, 5000, 1000, 10, 2000));
        when(userRepository.findAll()).thenReturn(users);

        // Act
        ArrayList<UserEntity> result = userService.getUsers();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsersReturnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        ArrayList<UserEntity> result = userService.getUsers();

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdSuccess() {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5, 5000, 1000, 10, 2000);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testRegisterUserWithInvalidAgeThrowsException() {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 0, "active", "Engineer", 5, 5000, 1000, 10, 2000);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("El valor del atributo age debe ser mayor a 0.", exception.getMessage());
    }
}

