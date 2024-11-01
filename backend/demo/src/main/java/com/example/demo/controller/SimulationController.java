package com.example.demo.controller;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.LoanEntity;
import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.entities.SimulationLoanEntity;
import com.example.demo.services.SimulationService;
import com.example.demo.services.UserService;
import com.example.demo.services.LoanAplicationService;
import com.example.demo.services.LoanService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/simulation")
@CrossOrigin("*")

public class SimulationController {

    @Autowired
    UserService usesrService;
    @Autowired
    LoanService loanService;
    @Autowired
    LoanAplicationService loanAplicationService;
    @Autowired
    SimulationService simulationService;


    @PostMapping("/aplication-simulation")
    public ResponseEntity<Double> simulateLoan(@RequestBody SimulationLoanEntity simulation){
        double result = simulationService.monthlyPaymentCal(simulation.getAmount(), simulation.getTerm(), simulation.getAnualInterestRate());
        return ResponseEntity.ok(result);
    }








}
