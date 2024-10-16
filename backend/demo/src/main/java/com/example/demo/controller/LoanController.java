package com.example.demo.controller;
import com.example.demo.entities.LoanEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/loan")
@CrossOrigin("*")
public class LoanController {
    @Autowired
    LoanService loanService;


    @GetMapping("/")
    public ResponseEntity<List<LoanEntity>> listLoansTypes(){
        List<LoanEntity> loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

    @PostMapping("/")
    public ResponseEntity<LoanEntity> registerLoan(@RequestBody LoanEntity loan){
        LoanEntity newLoan = loanService.saveLoan(loan);
        return ResponseEntity.ok(newLoan);
    }

    @PutMapping("/")
    public ResponseEntity<LoanEntity> updateLoan(@RequestBody LoanEntity loan){
        LoanEntity updateLoan = loanService.updateLoan(loan);
        return ResponseEntity.ok(updateLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) throws Exception {
        var isDeleted = loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }






}
