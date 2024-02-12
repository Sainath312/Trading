package com.example.Trading.services;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.entity.Symbol;
import com.example.Trading.model.ResponseMessage;
import com.example.Trading.repository.SymbolRepo;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
@Repository
public class SymbolService {

    @Value("${api.csvFilePath}")
    public String csvFilePath;

    @Autowired
    SymbolRepo symbolRepo;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Method to check if a symbol exists by symbol name
    public boolean existsBySymbol(String symbol) {
        String sql = "SELECT COUNT(*) FROM Symbol WHERE SYMBOL = :symbol";
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    // Method to find a symbol by symbol name
    public Symbol findSymbol(String symbol) {
        String sql = "SELECT * FROM Symbol WHERE SYMBOL = :symbol";
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        List<Symbol> symbols = namedParameterJdbcTemplate.query(sql, params, symbolRowMapper);
        return symbols.isEmpty() ? null : symbols.get(0);
    }

    // Method to find all symbols
    public List<Symbol> findAllSymbols() {
        String sql = "SELECT * FROM Symbol";
        return namedParameterJdbcTemplate.query(sql, symbolRowMapper);
    }

    // RowMapper to convert a ResultSet row to a Symbol object
    private final RowMapper<Symbol> symbolRowMapper = (resultSet, rowNum) -> {
        Symbol symbol = new Symbol();
        symbol.setSymbol(resultSet.getString("SYMBOL"));
        symbol.setSymbolName(resultSet.getString("SYMBOL_NAME"));
        symbol.setIndexName(resultSet.getString("INDEX_NAME"));
        symbol.setCompanyName(resultSet.getString("COMPANY_NAME")); // Corrected field name
        symbol.setIndustry(resultSet.getString("INDUSTRY"));
        symbol.setSeries(resultSet.getString("SERIES"));
        symbol.setIsinCode(resultSet.getString("ISIN_CODE"));
        symbol.setExchange(resultSet.getString("EXCHANGE"));
        symbol.setCreatedAt(resultSet.getString("CREATED_AT"));
        symbol.setUpdatedAt(resultSet.getString("UPDATED_AT"));
        symbol.setScripCode(resultSet.getString("SCRIP_CODE"));
        return symbol;
    };

    @Transactional
    public ResponseMessage storeCsvInDatabase() throws IOException {
        try {
            Reader reader = new FileReader(csvFilePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("SYMBOL", "SYMBOL_NAME", "INDEX_NAME", "COMPANY_NAME", "INDUSTRY", "SERIES", "ISIN_CODE", "EXCHANGE", "CREATED_AT", "UPDATED_AT", "SCRIP_CODE")
                    .withIgnoreHeaderCase()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser) {
                Symbol symbol = new Symbol();
                symbol.setSymbol(csvRecord.get("SYMBOL"));
                symbol.setSymbolName(csvRecord.get("SYMBOL_NAME"));
                symbol.setIndexName(csvRecord.get("INDEX_NAME"));
                symbol.setCompanyName(csvRecord.get("COMPANY_NAME")); // Corrected field name
                symbol.setIndustry(csvRecord.get("INDUSTRY"));
                symbol.setSeries(csvRecord.get("SERIES"));
                symbol.setIsinCode(csvRecord.get("ISIN_CODE"));
                symbol.setExchange(csvRecord.get("EXCHANGE"));
                symbol.setCreatedAt(csvRecord.get("CREATED_AT"));
                symbol.setUpdatedAt(csvRecord.get("UPDATED_AT"));
                symbol.setScripCode(csvRecord.get("SCRIP_CODE"));
                symbolRepo.save(symbol);
            }
            csvParser.close();
            reader.close();
            return new ResponseMessage(StringConstants.Saved);

        } catch (IOException e) {
            return new ResponseMessage(StringConstants.FailedToSave);
        }

    }
}
