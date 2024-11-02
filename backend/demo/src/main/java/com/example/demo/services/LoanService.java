package com.example.demo.services;

import com.example.demo.entities.LoanEntity;
import com.example.demo.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    public ArrayList<LoanEntity> getLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }

    public LoanEntity saveLoan(LoanEntity loan) {
        return loanRepository.save(loan);
    }

    public LoanEntity updateLoan(LoanEntity loan) {
        return loanRepository.save(loan);
    }

    public boolean deleteLoan(Long id) throws Exception {
        try {
            loanRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
