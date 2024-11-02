package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
// Importaciones de LoanAplicationServiceTest
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.LoanAplicationRepository;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.LoanAplicactionEntity;

class SimulationServiceTest {

    @InjectMocks
    private SimulationService simulationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanAplicationRepository loanAplicationRepository;
    @Mock
    private LoanAplicationService loanAplicationService;

    private UserEntity user;
    private LoanAplicactionEntity loan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        loan = new LoanAplicactionEntity();
        // ...inicializar user y loan...
    }

    @Test
    void testMonthlyPaymentCal() {
        double result = simulationService.monthlyPaymentCal(100000, 15, 5.5f);
        assertEquals(817.08, result, 0.01);
    }

    @Test
    void testFeeIncomeRelation() {
        loan.setFee(500);
        user.setIncome(5000);
        double result = simulationService.feeIncomeRelation(loan, user);
        assertEquals(10.0, result);
    }

    @Test
    void testDebtIncomeRelationCheck() {
        user.setDebt(2000);
        loan.setFee(300);
        user.setIncome(6000);
        boolean result = simulationService.debtIncomeRelationCheck(loan, user);
        assertTrue(result);
    }

    @Test
    void testDebtIncomeRelationCheck_whenFalse() {
        user.setDebt(2000);
        loan.setFee(300);
        user.setIncome(3000);
        boolean result = simulationService.debtIncomeRelationCheck(loan, user);
        assertFalse(result);
    }

    @Test
    void testAgeCheck() {
        user.setAge(69);
        boolean result = simulationService.ageCheck(user);
        assertTrue(result);
    }

    @Test
    void testAgeCheck_whenFalse() {
        user.setAge(75);
        boolean result = simulationService.ageCheck(user);
        assertFalse(result);
    }

    @Test
    void testYearsWorkCheck() {
        user.setWork("Empleado");
        user.setYears_working(2);
        boolean result = simulationService.yearsWorkCheck(user);
        assertTrue(result);
    }

    @Test
    void testYearsWorkCheck_whenFalse() {
        user.setWork("Independiente");
        user.setYears_working(1);
        boolean result = simulationService.yearsWorkCheck(user);
        assertFalse(result);
    }

    @Test
    void testMinBalanceRequiredCheck() {
        user.setBalanceAccount(15000);
        loan.setAmount(100000);
        boolean result = simulationService.minBalanceRequiredCheck(user, loan);
        assertTrue(result);
    }

    @Test
    void testMinBalanceRequiredCheck_whenFalse() {
        user.setBalanceAccount(5000);
        loan.setAmount(100000);
        boolean result = simulationService.minBalanceRequiredCheck(user, loan);
        assertFalse(result);
    }

    @Test
    void testBalanceYearsWorkingRelationCheck() {
        user.setYearsAccount(3);
        user.setBalanceAccount(12000);
        loan.setAmount(100000);
        boolean result = simulationService.balanceYearsWorkingRelationCheck(user, loan);
        assertTrue(result);
    }

    @Test
    void testBalanceYearsWorkingRelationCheck_whenFalse() {
        user.setYearsAccount(3);
        user.setBalanceAccount(2000);
        loan.setAmount(100000);
        boolean result = simulationService.balanceYearsWorkingRelationCheck(user, loan);
        assertFalse(result);
    }


    @Test
    void testSaveCapacityCheck_whenSolida() {
        // Todas las condiciones cumplidas (evaluation == 5)
        user.setBalanceAccount(15000);
        user.setYearsAccount(3);
        loan.setAmount(100000);
        loan.setConsistentSaveCheck(true);
        loan.setPeriodicDepositsCheck(true);
        loan.setRecentWithdrawCheck(false);
        boolean result = simulationService.saveCapacityCheck(loan, user);
        assertTrue(result);
        assertEquals("solida", loan.getSave_capacity());
    }

    @Test
    void testSaveCapacityCheck_whenModerada() {
        // 3 condiciones cumplidas (evaluation == 3)
        user.setBalanceAccount(5000);
        user.setYearsAccount(1);
        loan.setAmount(1000);
        loan.setConsistentSaveCheck(true);
        loan.setPeriodicDepositsCheck(false);
        loan.setRecentWithdrawCheck(false);
        boolean result = simulationService.saveCapacityCheck(loan, user);
        assertTrue(result);
        assertEquals("moderada", loan.getSave_capacity());
    }

    @Test
    void testSaveCapacityCheck_whenInsuficiente() {
        // Menos de 3 condiciones cumplidas (evaluation < 3)
        user.setBalanceAccount(2000);
        user.setYearsAccount(1);
        loan.setAmount(100000);
        loan.setConsistentSaveCheck(false);
        loan.setPeriodicDepositsCheck(false);
        loan.setRecentWithdrawCheck(true);
        boolean result = simulationService.saveCapacityCheck(loan, user);
        assertFalse(result);
        assertEquals("insuficiente", loan.getSave_capacity());
    }

    @Test
    void testTotalCostCal() {
        loan.setAmount(100000);
        loan.setTerm(180);
        loan.setAnualInterestRate(5.5f);
        loan.setCreditInsuarance(0.01);
        loan.setAdministrationCommission(0.02);
        loan.setMonthlyFireInsurance(50);
        double result = simulationService.totalCostCal(loan);
        double expectedTotalCost = 24625.40;
        assertEquals(expectedTotalCost, result, 0.01);
    }

}