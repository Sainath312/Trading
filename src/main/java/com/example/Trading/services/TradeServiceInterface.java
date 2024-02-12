package com.example.Trading.services;

import com.example.Trading.model.*;

import java.util.List;

public interface TradeServiceInterface {
    ResponseMessage cancelOrder(CancelOrder tradeRequest);

    List<Portfolio> getPortfolio();

    List<TradeHistory> getTradeHistory();

    ResponseMessage addOrder(AddOrder order);
}
