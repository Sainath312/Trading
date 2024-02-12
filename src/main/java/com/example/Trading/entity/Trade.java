package com.example.Trading.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userTradeId;

    private String stockSymbol;

    private String orderType;

    private double price;

    private int quantity;

    private String timestamp;

}