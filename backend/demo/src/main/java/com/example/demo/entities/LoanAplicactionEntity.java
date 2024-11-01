package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_aplication")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class LoanAplicactionEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false)
        private Long id;


        private boolean simulation; //
        private String rutUser;
        private String state; //State of de aplication (ej: E1, E2, ..., E7)
        private int amount;
        private float anualInterestRate; //Anual Percentage of interest
        private int term; //Years of loan
        private String loan_type;
        private double fee; //Month ly pay
        private double creditInsuarance; //Insurance percentage if user die
        private double monthlyFireInsurance; //Insurance percentage if house burn down
        private double administrationCommission; //Percentage of administration commission
        private double propertyValue; //Value of the proerty
        private boolean consistentSaveCheck; //Check for R7 true: has consitent Saves
        private boolean periodicDepositsCheck; //Check for R7 true: make periodic deposits
        private boolean recentWithdrawCheck; //Check for R7 false: hasnt withdraw a big amount
        private String save_capacity; // Solida, moderada o isuficiente

        @Lob
        private byte[] income_file;

        @Lob
        private byte[] certificadoAvaluo;

        @Lob
        private byte[] historialCrediticio;

        @Lob
        private byte[] escrituraPrimeraVivienda;

        @Lob
        private byte[] estadoFinancieroNegocio;

        @Lob
        private byte[] planNegocios;

        @Lob
        private byte[] presupuestoRemodelacion;

        @Lob
        private byte[] certificadoAvaluoActualizado;
    }
