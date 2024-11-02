package com.example.demo.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
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
        users.add(new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active", "Engineer", 5,
                5000, 1000, 10, 2000));
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
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active",
                "Engineer", 5, 5000, 1000, 10, 2000);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByRutSuccess() {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30, "active",
                "Engineer", 5, 5000, 1000, 10, 2000);
        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // Act
        UserEntity result = userService.getUserByRut("12345678-9");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findByRut("12345678-9");
    }

    @Test
    void testRegisterUserWithInvalidAgeThrowsException() {
        // Arrange
        UserEntity user = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 0, "active",
                "Engineer", 5, 5000, 1000, 10, 2000);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("El valor del atributo age debe ser mayor a 0.", exception.getMessage());
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        UserEntity user = new UserEntity(2L, "98765432-1", "newpassword", "newuser@mail.com", "Jane Smith", 28,
                "active", "Designer", 4, 6000, 500, 8, 2500);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Act
        UserEntity result = userService.registerUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testDeleteUserSuccess() throws Exception {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserThrowsException() {
        // Arrange
        Long userId = 1L;
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(userId);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUserSuccess() {
        // Arrange
        UserEntity existingUser = new UserEntity(1L, "12345678-9", "password", "test@mail.com", "John Doe", 30,
                "active",
                "Engineer", 5, 5000, 1000, 10, 2000);
        UserEntity updatedUser = new UserEntity(1L, "12345678-9", "newpassword", "newtest@mail.com", "John Doe", 31,
                "active",
                "Engineer", 6, 6000, 1500, 11, 3000);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        // Act
        UserEntity result = userService.updateUser(updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals("newpassword", result.getPassword());
        assertEquals("newtest@mail.com", result.getMail());
        assertEquals(31, result.getAge());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testUpdateUserNotFound() {
        // Arrange
        UserEntity updatedUser = new UserEntity(99L, "12345678-9", "newpassword", "newtest@mail.com", "John Doe", 31,
                "active",
                "Engineer", 6, 6000, 1500, 11, 3000);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(updatedUser);
        });

        assertEquals("El usuario con ID 99 no existe.", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }
}
