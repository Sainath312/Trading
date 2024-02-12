package com.example.Trading.model;

import lombok.Data;

@Data
public class Portfolio {
    private String stockSymbol;
    private int quantity;
    private String orderStatus;
    private String timestamp;
}
