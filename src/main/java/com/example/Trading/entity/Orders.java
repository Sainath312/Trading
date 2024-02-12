package com.example.Trading.entity;

import com.example.Trading.constants.StringConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userOrdersId;

    @NotBlank(message = StringConstants.StockSymbolNotEmpty)
    @Size(min = 10, max = 25, message = StringConstants.StockSymbolLength)
    private String stockSymbol;

    @Pattern(regexp =StringConstants.OrderLike, message = StringConstants.OrderType)
    private String orderType;

    @Positive(message =StringConstants.Price)
    @DecimalMin(value = "0.00", message = StringConstants.PriceConstraint)
    private double price;

    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = StringConstants.Quantity)
    private int quantity;

    private String status;

    private String timestamp;

}
