package com.example.demo.entities;


import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "type_loan")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String type;
    private int max_term; //In years
    private float interst_max;
    private float interst_min;
    private int max_loan;
    private int n_documents; //The number of document that client has to upload



}
