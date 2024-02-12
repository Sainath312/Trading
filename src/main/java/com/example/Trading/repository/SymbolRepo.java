package com.example.Trading.repository;

import com.example.Trading.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepo extends JpaRepository<Symbol,String> {

}
