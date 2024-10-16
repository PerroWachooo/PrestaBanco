package com.example.demo.repositories;

import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LoanAplicationRepository extends JpaRepository<LoanAplicactionEntity, Long> {


    public List<LoanAplicactionEntity> findByState(String state);


    public ArrayList<LoanAplicactionEntity> findAllByRutUser(String rutUser);








}
