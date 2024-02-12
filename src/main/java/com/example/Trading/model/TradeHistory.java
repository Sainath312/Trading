package com.example.Trading.model;

import lombok.Data;

@Data
public class TradeHistory {
    private String stockSymbol;
    private int quantity;
    private double price;
    private String orderType;
    private String orderStatus;
    private String timestamp;
}