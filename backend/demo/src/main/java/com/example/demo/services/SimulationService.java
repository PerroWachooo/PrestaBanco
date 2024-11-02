package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.entities.LoanEntity;
import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.repositories.LoanAplicationRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static java.lang.Math.round;

@Service
public class SimulationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanAplicationRepository loanAplicationRepository;
    @Autowired
    LoanAplicationService loanAplicationService;

    @Autowired

    SimulationService simulationService;

    // P1
    // Simulation of credit
    // p: monto del prestamo
    // n: Plazo del prestamo
    // r: tasa de intereses
    public double monthlyPaymentCal(int p, int n, float r) {
        int monthlypay = n * 12; // Número de meses
        float monthlyfee = r / 12 / 100; // Tasa de interés mensual

        double fracUp = monthlyfee * Math.pow(1 + monthlyfee, monthlypay);
        double fracDown = Math.pow(1 + monthlyfee, monthlypay) - 1;

        double m = p * (fracUp / fracDown);

        // Redondear el resultado a dos decimales
        BigDecimal bd = new BigDecimal(m).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public double feeIncomeRelation(LoanAplicactionEntity loan, UserEntity user) {
        double fee = loan.getFee();
        int income = user.getIncome();
        return (fee / income) * 100;

    }

    public boolean debtIncomeRelationCheck(LoanAplicactionEntity loan, UserEntity user) {
        double totalDebt = user.getDebt() + loan.getFee();
        int halfIncome = user.getIncome() / 2;
        if (halfIncome < totalDebt) {

            System.out.println("Loan Aplication Rejected, Debt Income Relation didnt meet the requirment");
            return false;
        } else {
            return true;
        }
    }

    public boolean ageCheck(UserEntity user) {
        if (user.getAge() >= 70) {
            System.out.println("Loan Aplication Rejected, too old");
            return false;
        } else {
            return true;
        }
    }

    public boolean yearsWorkCheck(UserEntity user) {
        if (user.getWork() != "Independiente" && user.getYears_working() >= 1) {
            return true;

        } else {
            return false; // If the job is indepdent or have less than 1 year working
        }

    }

    // R7.1
    public boolean minBalanceRequiredCheck(UserEntity user, LoanAplicactionEntity loan) {
        if (user.getBalanceAccount() >= loan.getAmount() * 0.1) { // The client need at leas 10% in their acount
            return true;
        } else {
            return false;
        }
    }

    // R7.4
    public boolean balanceYearsWorkingRelationCheck(UserEntity user, LoanAplicactionEntity loan) {
        if (user.getYearsAccount() < 2) {
            if (user.getBalanceAccount() >= loan.getAmount() * 0.2) {
                return true;
            }
        } else {
            if (user.getBalanceAccount() >= loan.getAmount() * 0.1) {
                return true;
            }
        }

        return false;
    }

    // R7
    // Desc: Check all the requirment from R7
    public boolean saveCapacityCheck(LoanAplicactionEntity loan, UserEntity user) {
        int evaluation = 0;
        if (minBalanceRequiredCheck(user, loan)) {
            evaluation += 1;
        }
        if (loan.isConsistentSaveCheck()) {
            evaluation += 1;
        }
        if (loan.isPeriodicDepositsCheck()) {
            evaluation += 1;
        }
        if (balanceYearsWorkingRelationCheck(user, loan)) {
            evaluation += 1;
        }
        if (!loan.isRecentWithdrawCheck()) {
            evaluation += 1;
        }

        if (evaluation == 5) {
            loan.setSave_capacity("solida");
            loanAplicationService.updateLoan(loan);
            return true;
        } else if (evaluation == 3 || evaluation == 4) {
            loan.setSave_capacity("moderada");
            loanAplicationService.updateLoan(loan);
            return true;
        } else {
            loan.setSave_capacity("insuficiente");
            loanAplicationService.updateLoan(loan);
            return false;
        }
    }

    // P6
    // Desc: Calculate total costs

    public double totalCostCal(LoanAplicactionEntity loan) {
        double monthlyPayment = monthlyPaymentCal(loan.getAmount(), loan.getTerm(),
                loan.getAnualInterestRate());
        double monthlyCreditInsurance = loan.getCreditInsuarance() * loan.getAmount();
        double AdministrationCommission = loan.getAdministrationCommission() * loan.getAmount();

        double monthlyCost = monthlyPayment + monthlyCreditInsurance + loan.getMonthlyFireInsurance();
        int months = loan.getTerm() / 12;

        return monthlyCost * months + AdministrationCommission;

    }

}
