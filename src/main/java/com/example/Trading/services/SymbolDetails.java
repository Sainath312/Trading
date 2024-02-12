package com.example.Trading.services;

import com.example.Trading.entity.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SymbolDetails {
    public static final Logger logger = LoggerFactory.getLogger(SymbolDetails.class);

    @Autowired
    SymbolService symbolService;


    //Retrieve the Symbol Details
    public List<Symbol> getSymbol(String symbol){
        logger.info("Returned by quote service method : RetrieveSymbolDetails");
        return Collections.singletonList(symbolService.findSymbol(symbol));
    }

    //Retrieve The All Symbols From SymbolsService
    @Cacheable(value ="cartCache",key = "'getAllSymbols'",unless = "#result==null")
    public List<Symbol> getAllSymbols() {   //get all the symbol details from symbol table
        logger.info("Returned by Quote service method : RetrieveAllSymbols");
        return symbolService.findAllSymbols();
    }

}
