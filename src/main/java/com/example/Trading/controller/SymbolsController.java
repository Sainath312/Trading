package com.example.Trading.controller;

import com.example.Trading.constants.ApiConstants;
import com.example.Trading.entity.Symbol;
import com.example.Trading.model.QuiteModel;
import com.example.Trading.model.ResponseMessage;
import com.example.Trading.services.SymbolDetails;
import com.example.Trading.services.SymbolService;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SymbolsController {

    @Autowired
    SymbolDetails serviceDetails;
    @Autowired
    SymbolService symbolService;

    @PostMapping("/importCSV")
    public ResponseMessage saveCsvFile() throws IOException {
         return symbolService.storeCsvInDatabase();
    }

    @PostMapping(ApiConstants.symbolDetails)               //Retrieve the symbol details from symbol table based on SYMBOL
    public List<Symbol> getSymbolDetails(@RequestBody @NotNull QuiteModel request) {
        String symbolName = request.getSymbolName();
        return serviceDetails.getSymbol(symbolName);
    }


    @GetMapping(ApiConstants.getAllSymbols)       //Retrieve all symbols details
    public List<Symbol> getAllSymbolDetails() {
        return serviceDetails.getAllSymbols();
    }

}
