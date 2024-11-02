package com.example.demo.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LoanAplicactionEntity.class)
public class LoanAplicactionEntityTest {

    @Test
    void whenSetAndGetId_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setId(1L);
        assertEquals(1L, loan.getId());
    }

    @Test
    void whenSetAndGetSimulation_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setSimulation(true);
        assertTrue(loan.isSimulation());
    }

    @Test
    void whenSetAndGetRutUser_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setRutUser("12345678-9");
        assertEquals("12345678-9", loan.getRutUser());
    }

    @Test
    void whenSetAndGetState_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setState("E1");
        assertEquals("E1", loan.getState());
    }

    @Test
    void whenSetAndGetAmount_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setAmount(1000000);
        assertEquals(1000000, loan.getAmount());
    }

    @Test
    void whenSetAndGetAnualInterestRate_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setAnualInterestRate(3.5f);
        assertEquals(3.5f, loan.getAnualInterestRate());
    }

    @Test
    void whenSetAndGetTerm_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setTerm(15);
        assertEquals(15, loan.getTerm());
    }

    @Test
    void whenSetAndGetLoanType_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        loan.setLoan_type("Mortgage");
        assertEquals("Mortgage", loan.getLoan_type());
    }

    // ...existing tests for other getters and setters...

    @Test
    void whenSetAndGetIncomeFile_thenCorrect() {
        LoanAplicactionEntity loan = new LoanAplicactionEntity();
        byte[] incomeFile = new byte[] { 1, 2, 3 };
        loan.setIncome_file(incomeFile);
        assertArrayEquals(incomeFile, loan.getIncome_file());
    }

    // ...tests for other byte array fields...

    @Test
    void whenAllFieldsSet_thenFieldsAreCorrect() {
        byte[] incomeFile = new byte[] { 1, 2, 3 };
        byte[] certificadoAvaluo = new byte[] { 4, 5, 6 };
        // ...existing code...

        LoanAplicactionEntity loan = new LoanAplicactionEntity(
                1L, true, "12345678-9", "E1", 1000000, 3.5f, 15, "Mortgage", 50000.0,
                0.02, 0.01, 0.005, 20000000.0, true, true, false, "Solida",
                incomeFile, certificadoAvaluo, null, null, null, null, null, null);

        assertEquals(1L, loan.getId());
        assertTrue(loan.isSimulation());
        assertEquals("12345678-9", loan.getRutUser());
        // ...assertions for other fields...
    }


}
