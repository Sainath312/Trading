package com.example.Trading.services;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.entity.Orders;
import com.example.Trading.entity.Trade;
import com.example.Trading.entity.UserInfo;
import com.example.Trading.exceptions.NotFoundException;
import com.example.Trading.exceptions.UserNotExists;
import com.example.Trading.model.*;
import com.example.Trading.repository.OrdersRepo;
import com.example.Trading.repository.TradeRepo;
import com.example.Trading.repository.UserInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.Trading.filter.JwtAuthFilter.userNameInToken;

@Service
@EnableScheduling
public class TradeServices implements TradeServiceInterface {
    public static final Logger logger = LoggerFactory.getLogger(TradeServices.class);

    @Autowired UserInfoRepo userInfoRepo;

    @Autowired OrdersRepo ordersRepo;

    @Autowired TradeRepo tradeRepo;

    @Autowired SymbolService symbolService;

    public String currentTime() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        String pattern = StringConstants.TimeStampPattern;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentTimestamp.format(formatter);
    }


    public UserInfo currentUser() {
        Optional<UserInfo> existingUserByUsername = userInfoRepo.findByUserName(userNameInToken);
        Optional<UserInfo> existingUserByEmail = userInfoRepo.findByEmail(userNameInToken);

        // Check if a user with the given username or email exists
        if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
            return existingUserByUsername.orElseGet(existingUserByEmail::get);
        } else {
            throw new UserNotExists(StringConstants.User_Not_Exists);
        }
    }

    //Add an Order
    public ResponseMessage addOrder(AddOrder orders) {
        Orders order = orders.getOrder();
        String symbol = order.getStockSymbol();
        UserInfo user = currentUser();
        if (symbolService.existsBySymbol(symbol)) {
            order.setUserOrdersId(currentUser());
            order.setStatus(StringConstants.Pending);
            order.setTimestamp(currentTime());
            user.getOrders().add(order);
            userInfoRepo.save(user);
            return new ResponseMessage(StringConstants.Success);
        } else {
            throw new NotFoundException(StringConstants.Symbol);
        }
    }

    //Cancel an existing Order
    public ResponseMessage cancelOrder(CancelOrder tradeRequest) {
        long orderId = Long.parseLong(tradeRequest.getOrderId());
        Optional<Orders> checkOrder = ordersRepo.findById(orderId);
        if (checkOrder.isPresent()) {
            Orders order = checkOrder.get();

            if (order.getUserOrdersId().getUserId() == (currentUser().getUserId())) {

                // Update the order status to CANCEL
                if (order.getStatus().equals(StringConstants.Pending)) {
                    order.setStatus(StringConstants.Cancelled);
                    order.setTimestamp(currentTime());
                } else {
                    return new ResponseMessage(StringConstants.OrderExecuted);
                }
                ordersRepo.save(order);

                return new ResponseMessage(StringConstants.CancelOrder);
            } else {
                return new ResponseMessage(StringConstants.ValidOrderId);
            }
        } else {
            throw new NotFoundException(StringConstants.OrderNotFound);
        }
    }

    @Scheduled(fixedRate = 30000)
    public void executeOrders() {
        logger.info("---->Executing orders");
        List<Orders> buyOrders = ordersRepo.findBuyOrders();
        List<Orders> sellOrders = ordersRepo.findSellOrders();
        for (Orders buyOrder : buyOrders) {
            for (Orders sellOrder : sellOrders) {
                executeOrder(buyOrder, sellOrder);
            }
        }
    }


    private void executeOrder(Orders buyOrder, Orders sellOrder) {

        if (buyOrder.getStockSymbol().equals(sellOrder.getStockSymbol()) &&
                buyOrder.getPrice() >= sellOrder.getPrice()) {

            // Check if both buy and sell orders have a status of "PENDING"
            if (buyOrder.getStatus().equals(StringConstants.Pending) && sellOrder.getStatus().equals(StringConstants.Pending)) {
                int transactionQuantity = calculateTransactionQuantity(buyOrder, sellOrder);

                 if (transactionQuantity > 0) {
                    // Create a trade for the buy side
                     Trade buyTrade = createTrade(buyOrder, StringConstants.Buy, transactionQuantity);

                     // Create a trade for the sell side
                    Trade sellTrade = createTrade(sellOrder, StringConstants.Sell, transactionQuantity);

                    updateUserTradeHistory(buyOrder.getUserOrdersId(), buyTrade);
                    updateUserTradeHistory(sellOrder.getUserOrdersId(), sellTrade);

                    updateOrder(buyOrder, transactionQuantity);
                    updateOrder(sellOrder, transactionQuantity);
                }
            }
        }
    }

    private void updateUserTradeHistory(UserInfo user, Trade trade) {
        user.getTrade().add(trade);
        userInfoRepo.save(user);
    }

    private Trade createTrade(Orders order, String orderType, int quantity) {
        Trade trade = new Trade();
        trade.setUserTradeId(order.getUserOrdersId());
        trade.setStockSymbol(order.getStockSymbol());
        trade.setOrderType(orderType);
        trade.setQuantity(quantity);
        trade.setPrice(order.getPrice());
        trade.setTimestamp(String.valueOf(LocalDateTime.now()));
        return trade;
    }

    private void updateOrder(Orders order, int quantity) {
        order.setQuantity(order.getQuantity() - quantity);
        if (order.getQuantity() == 0) {
            order.setStatus(StringConstants.EXECUTED);
        }
        ordersRepo.save(order);
    }

    private int calculateTransactionQuantity(Orders buyOrder, Orders sellOrder) {
        return Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
    }





public List<Portfolio> getPortfolio() {
    List<Portfolio> portfolio = new ArrayList<>();
    UserInfo user = currentUser();
    List<Portfolio> sellOrders = ordersRepo.findByUserOrdersIdAndOrderType(currentUser(), StringConstants.Sell)
            .stream()
            .filter(order -> order.getQuantity() > 0 && order.getStatus().equals(StringConstants.Pending))
            .map(order -> createPortfolio(order.getStockSymbol(),order.getQuantity(),StringConstants.Pending, order.getTimestamp()))
            .toList();

    List<Portfolio> buyOrders = tradeRepo.findByUserTradeIdAndOrderType(currentUser(), StringConstants.Buy).stream()
            .filter(trade -> trade.getQuantity() > 0)
            .map(trade -> createPortfolio(trade.getStockSymbol(), trade.getQuantity(), StringConstants.EXECUTED, trade.getTimestamp()))
            .toList();
    portfolio.addAll(sellOrders);
    portfolio.addAll(buyOrders);

    return portfolio;
}

    //Create a Portfolio Item using Portfolio
    private Portfolio createPortfolio(String stockSymbol, int quantity, String orderStatus, String timestamp) {
        Portfolio portfolioItem = new Portfolio();
        portfolioItem.setStockSymbol(stockSymbol);
        portfolioItem.setQuantity(quantity);
        portfolioItem.setOrderStatus(orderStatus);
        portfolioItem.setTimestamp(timestamp);
        return portfolioItem;
    }


    //Get Current User's Trade History
    public List<TradeHistory> getTradeHistory() {
        List<TradeHistory> tradeHistory = new ArrayList<>();

        List<TradeHistory> orders = ordersRepo.findByUserOrdersId(currentUser())
                .stream()
                .filter(order -> order.getQuantity() > 0 && !order.getStatus().equals(StringConstants.EXECUTED))
                .map(order -> createTradeHistoryItem(order.getStockSymbol(), order.getQuantity(), order.getStatus(), order.getPrice(), order.getOrderType(), order.getTimestamp()))
                .toList();

        List<TradeHistory> trades = tradeRepo.findByUserTradeId(currentUser())
                .stream()
                .map(trade -> createTradeHistoryItem(trade.getStockSymbol(), trade.getQuantity(), StringConstants.EXECUTED, trade.getPrice(), trade.getOrderType(), trade.getTimestamp()))
                .toList();
        tradeHistory.addAll(orders);
        tradeHistory.addAll(trades);

        return tradeHistory;
    }


    //Create a TradeHistoryItem using TradeHistoryDTO
    private TradeHistory createTradeHistoryItem(String stockSymbol, int quantity, String orderStatus, double price, String orderType, String timestamp) {
        TradeHistory tradeHistoryItem = new TradeHistory();
        tradeHistoryItem.setStockSymbol(stockSymbol);
        tradeHistoryItem.setQuantity(quantity);
        tradeHistoryItem.setOrderStatus(orderStatus);
        tradeHistoryItem.setPrice(price);
        tradeHistoryItem.setOrderType(orderType);
        tradeHistoryItem.setTimestamp(timestamp);
        return tradeHistoryItem;
    }


}
