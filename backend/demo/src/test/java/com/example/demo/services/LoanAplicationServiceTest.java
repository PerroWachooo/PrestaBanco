package com.example.demo.services;

import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.repositories.LoanAplicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@WebMvcTest(LoanAplicationService.class)
public class LoanAplicationServiceTest {

    @Autowired
    private LoanAplicationService loanAplicationService;

    @MockBean
    private LoanAplicationRepository loanAplicationRepository;

    @Test
    void whenGetLoans_thenReturnLoanList() {
        LoanAplicactionEntity loan1 = new LoanAplicactionEntity();
        LoanAplicactionEntity loan2 = new LoanAplicactionEntity();
        LoanAplicactionEntity loan3 = new LoanAplicactionEntity();

        ArrayList<LoanAplicactionEntity> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);

        when(loanAplicationRepository.findAll()).thenReturn(loans);

        ArrayList<LoanAplicactionEntity> result = loanAplicationService.getLoans();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result).isEqualTo(loans);
    }

    @Test
    void whenGetLoanById_thenReturnLoan() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setId(1L);

        when(loanAplicationRepository.findById(1L)).thenReturn(Optional.of(loan));

        Optional<LoanAplicactionEntity> result = loanAplicationService.getLoanAplicationById(1L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void whenGetLoansByRut_thenReturnLoanList() {
        LoanAplicactionEntity loan1 = new LoanAplicactionEntity();
        loan1.setRutUser("12345678-9");
        LoanAplicactionEntity loan2 = new LoanAplicactionEntity();
        loan2.setRutUser("12345678-9");

        ArrayList<LoanAplicactionEntity> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);

        when(loanAplicationRepository.findAllByRutUser("12345678-9")).thenReturn(loans);

        ArrayList<LoanAplicactionEntity> result = loanAplicationService.getLoansByRut("12345678-9");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRutUser()).isEqualTo("12345678-9");
    }

    @Test
    void whenSaveLoan_thenReturnSavedLoan() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setId(1L);
        loan.setAmount(1000000);

        when(loanAplicationRepository.save(loan)).thenReturn(loan);

        LoanAplicactionEntity result = loanAplicationService.saveLoan(loan);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAmount()).isEqualTo(1000000);
    }

    @Test
    void whenUpdateLoan_thenReturnUpdatedLoan() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setId(1L);
        loan.setAmount(1000000);

        when(loanAplicationRepository.save(loan)).thenReturn(loan);

        LoanAplicactionEntity result = loanAplicationService.updateLoan(loan);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAmount()).isEqualTo(1000000);
    }

    @Test
    void whenUpdateStateLoan_thenReturnLoanWithUpdatedState() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setId(1L);
        loan.setState("E1");

        when(loanAplicationRepository.save(loan)).thenReturn(loan);

        LoanAplicactionEntity result = loanAplicationService.updateStateLoan(loan, "E2");

        assertThat(result.getState()).isEqualTo("E2");
    }

    @Test
    void whenDeleteLoan_thenReturnTrue() throws Exception {
        Long loanId = 1L;

        boolean result = loanAplicationService.deleteLoan(loanId);

        assertThat(result).isTrue();
    }

    @Test
    void whenDeleteLoanFails_thenThrowException() throws Exception {
        Long loanId = 5L;

        doThrow(new RuntimeException("Delete failed")).when(loanAplicationRepository).deleteById(loanId);

        Exception exception = assertThrows(Exception.class, () -> {
            loanAplicationService.deleteLoan(loanId);
        });

        assertThat(exception.getMessage()).isEqualTo("Delete failed");
    }
}
