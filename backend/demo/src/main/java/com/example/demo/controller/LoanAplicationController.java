package com.example.demo.controller;

import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.LoanAplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loanaplication")
@CrossOrigin("*")


public class LoanAplicationController {
    @Autowired
    LoanAplicationService loanAplicationService;

    @GetMapping("/")
    public ResponseEntity<List<LoanAplicactionEntity>> listAllAplications(){
        List<LoanAplicactionEntity> loansAplications =loanAplicationService.getLoans();
        return ResponseEntity.ok(loansAplications);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<LoanAplicactionEntity>> listAplicationsByRut(@RequestBody UserEntity user){
        List<LoanAplicactionEntity> loansAplications =loanAplicationService.getLoansByRut(user.getRut());
        return ResponseEntity.ok(loansAplications);
    }

    @PostMapping("/")
    public ResponseEntity<LoanAplicactionEntity> registerLoanAplication(@RequestBody LoanAplicactionEntity loanAplicaction){
        LoanAplicactionEntity newLoanAplication = loanAplicationService.saveLoan(loanAplicaction);
        return ResponseEntity.ok(newLoanAplication);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) throws Exception {
        var isDeleted = loanAplicationService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }


}
