package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_user")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut; // xx.xxx.xxx-x
    private String password;
    private String mail;
    private String name;
    private int age;

    private String state_hist; //?? Que wea era esto?

    private String work;
    private int years_working;
    private int income;
    private int debt;
    private int yearsAccount;
    private int balanceAccount;


}
