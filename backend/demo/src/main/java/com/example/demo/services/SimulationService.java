package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.entities.LoanEntity;
import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.repositories.LoanAplicationRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import static java.lang.Math.round;

public class SimulationService {

    UserRepository userRepository;
    LoanAplicationRepository loanAplicationRepository;
    LoanAplicationService loanAplicationService;

    SimulationService simulationService;


    //P1
    //Simulation of credit
    public double monthlyPaymentCal(LoanAplicactionEntity loan) {
        int p = loan.getAmount();
        int n = loan.getTerm() * 12; //Number of month to pay
        float r = loan.getAnualInterestRate() / 12; //Monthly fee rate

        double fracUp = r * (Math.pow((1 + r), n));
        double fracDown = Math.pow((1 + r), n) - 1;

        double m = p * fracUp / fracDown;
        loan.setFee(m);
        loanAplicationRepository.save(loan);

        return loan.getFee();
    }

    public double feeIncomeRelation(LoanAplicactionEntity loan, UserEntity user) {
        double fee = loan.getFee();
        int income = user.getIncome();
        return (fee / income) * 100;

    }

    public boolean debtIncomeRelationCheck(LoanAplicactionEntity loan, UserEntity user) {
        while (user.getDebt() >= 0 && loan.getFee() != 0) {
            double totalDebt = user.getDebt() + loan.getFee();
            int halfIncome = user.getIncome() / 2;

            if (halfIncome < totalDebt) {
                System.out.println("Loan Aplication Rejected, Debt Income Relation didnt meet the requirment");
                return false;
            } else {
                return true;
            }
        }
        throw new IllegalArgumentException("Debt or Fee are incorrect, pleasef check");

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
            return false; //If the job is indepdent or have less than 1 year working
        }


    }


    //R7.1
    public boolean minBalanceRequiredCheck(UserEntity user, LoanAplicactionEntity loan) {
        if (user.getBalanceAccount() >= loan.getAmount() * 0.1) { //The client need at leas 10% in their acount
            return true;
        } else {
            return false;
        }
    }


    //R7.4
    public boolean balanceYearsWorkingRelationCheck(UserEntity user, LoanAplicactionEntity loan) {
        if (user.getYearsAccount() < 2) {
            if (user.getBalanceAccount() >= loan.getAmount() * 0.2) {
                return true;
            }
        } else if (user.getYearsAccount() >= 2) {
            if (user.getBalanceAccount() >= loan.getAmount() * 0.1) {
                return true;
            }
        }

        return false;
    }


    //R7
    //Desc: Check all the requirment from R7
    public boolean saveCapacityCheck(LoanAplicactionEntity loan, UserEntity user){
        int evaluation = 0;
        if(minBalanceRequiredCheck(user,loan)){evaluation+=1;}
        if(loan.isConsistentSaveCheck()){evaluation+=1;}
        if(loan.isPeriodicDepositsCheck()){evaluation+=1;}
        if(balanceYearsWorkingRelationCheck(user,loan)){evaluation=+1;}
        if(!loan.isRecentWithdrawCheck()){evaluation=+1;}

        if(evaluation == 5) {
            loan.setSave_capacity("solida");
            loanAplicationService.updateLoan(loan);
            return true;
        }
        else if (evaluation== 3 || evaluation==4) {
            loan.setSave_capacity("moderada");
            loanAplicationService.updateLoan(loan);
            return true;
        }
        else {
            loan.setSave_capacity("insuficiente");
            loanAplicationService.updateLoan(loan);
            return false;
        }
    }


    //P6
    //Desc: Calculate total costs

    public double totalCostCal(LoanAplicactionEntity loan) {
        double monthlyPayment = simulationService.monthlyPaymentCal(loan);
        double monthlyCreditInsurance = loan.getCreditInsuarance()*loan.getAmount();
        double AdministrationCommission = loan.getAdministrationCommission()*loan.getAmount();

        double monthlyCost = monthlyPayment + monthlyCreditInsurance + loan.getMonthlyFireInsurance();
        int months = loan.getTerm()/12;

        return  monthlyCost * months + AdministrationCommission;

    }


}




