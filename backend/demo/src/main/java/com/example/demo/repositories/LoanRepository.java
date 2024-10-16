package com.example.demo.repositories;

import com.example.demo.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long>{
    public LoanEntity findLoanEntityByType(String type);
    public LoanEntity findLoanEntityById(Long id);
}
