package com.example.demo.entities;

public class SimulationLoanEntity {


        private int amount;
        private int term;
        private Float anualInterestRate;

        // Getters y Setters
        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public Float getAnualInterestRate() {
            return anualInterestRate;
        }

        public void setAnualInterestRate(Float anualInterestRate) {
            this.anualInterestRate = anualInterestRate;
        }
    }


