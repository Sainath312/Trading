package com.example.Trading.controller;

import com.example.Trading.constants.ApiConstants;
import com.example.Trading.model.*;
import com.example.Trading.services.TradeServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TradeController {
    public static final Logger logger = LoggerFactory.getLogger(TradeController.class);


    @Autowired
    TradeServiceInterface service;

    @PostMapping(ApiConstants.AddOrder)   // Adding order
    public ResponseMessage addOrder(@RequestBody AddOrder order)
    {
        logger.info("Adding the Order");
        return service.addOrder(order);
    }

    @PostMapping(ApiConstants.CancelOrder)
    public ResponseMessage cancelOrder(@RequestBody CancelOrder tradeRequest)
    {
        logger.info("Canceling the Order");
        return service.cancelOrder(tradeRequest);
 }


    @GetMapping(ApiConstants.GetPortfolio)  //get user's Portfolio
    public String getPortfolio() {
        logger.info("Retrieved portfolio List");
        List<Portfolio> portfolio = service.getPortfolio();
        return portfolio != null ? portfolio.toString() : "No Pending Status";

    }


    @GetMapping(ApiConstants.GetTradeHistory)   //get User's Trade History
    public String getTradeHistory() {
        logger.info("Retrieved tradeHistory List");
        List<TradeHistory> tradeHistory = service.getTradeHistory();
        return tradeHistory != null ? tradeHistory.toString() : "No Trade History";
    }

    

}
