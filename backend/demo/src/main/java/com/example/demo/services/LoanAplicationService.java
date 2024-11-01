package com.example.demo.services;
import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.repositories.LoanAplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class LoanAplicationService {
    @Autowired
    LoanAplicationRepository loanAplicationRepository;


    public ArrayList<LoanAplicactionEntity> getLoans(){
        return(ArrayList<LoanAplicactionEntity>) loanAplicationRepository.findAll();

    }



    public Optional <LoanAplicactionEntity> getLoanAplicationById(Long id){return loanAplicationRepository.findById(id);}



    public ArrayList<LoanAplicactionEntity> getLoansByRut(String rut){
        return(ArrayList<LoanAplicactionEntity>) loanAplicationRepository.findAllByRutUser(rut);
    }

    public LoanAplicactionEntity saveLoan (LoanAplicactionEntity loan){
        return loanAplicationRepository.save(loan);
    }

    public LoanAplicactionEntity updateLoan (LoanAplicactionEntity loan){
        return loanAplicationRepository.save(loan);
    }


    public LoanAplicactionEntity updateStateLoan(LoanAplicactionEntity loan, String newState){
        loan.setState(newState);
        return loanAplicationRepository.save(loan);
    }

    public boolean deleteLoan(Long id) throws Exception {
        try{
            loanAplicationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }





}
