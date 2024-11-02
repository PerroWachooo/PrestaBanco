package com.example.demo.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entities.LoanEntity;
import com.example.demo.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Optional;

public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLoansReturnsNonEmptyList() {
        // Arrange
        ArrayList<LoanEntity> loans = new ArrayList<>();
        loans.add(new LoanEntity(1L, "Personal", 20, 3.5f, 2.0f, 1500000, 3));
        when(loanRepository.findAll()).thenReturn(loans);

        // Act
        ArrayList<LoanEntity> result = loanService.getLoans();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testSaveLoan() {
        // Arrange
        LoanEntity loan = new LoanEntity(1L, "Personal", 20, 3.5f, 2.0f, 1500000, 3);
        when(loanRepository.save(loan)).thenReturn(loan);

        // Act
        LoanEntity result = loanService.saveLoan(loan);

        // Assert
        assertThat(result).isEqualTo(loan);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void updateLoan() {
        // Arrange
        LoanEntity loan = new LoanEntity(1L, "Personal", 20, 3.5f, 2.0f, 1500000, 3);
        when(loanRepository.save(loan)).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoan(loan);

        // Assert
        assertThat(result).isEqualTo(loan);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void deleteLoan() {
        // Arrange
        Long id = 1L;
        doNothing().when(loanRepository).deleteById(id);

        // Act
        boolean result = false;
        try {
            result = loanService.deleteLoan(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
