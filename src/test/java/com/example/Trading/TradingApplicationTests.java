package com.example.Trading;


import com.example.Trading.entity.Orders;
import com.example.Trading.entity.UserInfo;
import com.example.Trading.exceptions.NotFoundException;
import com.example.Trading.exceptions.UserNotExists;
import com.example.Trading.model.AddOrder;
import com.example.Trading.model.CancelOrder;


import com.example.Trading.model.ResponseMessage;
import com.example.Trading.repository.OrdersRepo;
import com.example.Trading.repository.UserInfoRepo;

import com.example.Trading.services.TradeServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class TradingApplicationTests {
	@Autowired
	 TradeServices tradeService;

	@Autowired
	 UserInfoRepo userInfoRepository;

	@Autowired
	 OrdersRepo ordersRepository;

	// Mocks for dependencies
	UserInfo mockDemoUser;
	Orders mockOrder;


	@BeforeEach
	public void setupDemoUserAndOrder() {
		// Create a demo user (assuming you have a user creation mechanism)
		mockDemoUser = new UserInfo();
		mockDemoUser.setUserName("Sai@123");
		mockDemoUser.setPassword("password");
		mockDemoUser.setEmail("sainath02@gmail.com");
		mockDemoUser.setMobileNumber("9874567891");
		mockDemoUser.setUserId(1L); // Set a valid user ID
		userInfoRepository.save(mockDemoUser);

		// Create a mock order
		mockOrder = new Orders();
		mockOrder.setStockSymbol("STK_ADANIPORTS_EQ_NSE");
		mockOrder.setQuantity(10);
		mockOrder.setUserOrdersId(mockDemoUser);
		mockOrder.setPrice(123.00);
		mockOrder.setId(1L); // Set a valid order ID
		ordersRepository.save(mockOrder);
	}
	@Test
	public void testAddOrderForNonExistentUser() {
		// Create an AddOrder object for a non-existent user
		AddOrder addOrder = new AddOrder();
		Orders order = new Orders();
		order.setStockSymbol("STK_ADANIPORTS_EQ_NSE");
		order.setQuantity(10);
		order.setPrice(123.00);
		// Assuming you have a user ID that doesn't exist in the database
		order.setUserOrdersId(new UserInfo());
		addOrder.setOrder(order);

		// Assert that adding the order for a non-existent user throws a UserNotExists exception
		assertThrows(UserNotExists.class, () -> tradeService.addOrder(addOrder));
	}
	@Test
	public void testAddAndCancelOrderForDemoUser() {
		// Create an AddOrder object for the demo user
		AddOrder addOrder = new AddOrder();
		Orders order = new Orders();
		order.setStockSymbol("STK_ADANIPORTS_EQ_NSE");
		order.setQuantity(10);
		order.setPrice(123.00);
		order.setOrderType("BUY");
		order.setStatus("PENDING");
		addOrder.setOrder(order);

		// Add the order
		ResponseMessage addOrderResponse = tradeService.addOrder(addOrder);

		// Assert that the addOrder operation was successful
		assertEquals("Success", addOrderResponse.getMessage());

		// Now, create a CancelOrder object for the same demo user and the order ID you just added
		CancelOrder cancelOrder = new CancelOrder();
		cancelOrder.setOrderId(String.valueOf(mockOrder.getId()));

		// Cancel the order
		ResponseMessage cancelOrderResponse = tradeService.cancelOrder(cancelOrder);

		// Assert that the cancelOrder operation was successful
		assertEquals("CancelOrder", cancelOrderResponse.getMessage());
	}



	@Test
	public void testCancelOrderWithInvalidOrderId() {
		// Create a CancelOrder object with an invalid order ID
		CancelOrder cancelOrder = new CancelOrder();
		cancelOrder.setOrderId("12345"); // An order ID that doesn't exist in the database

		// Assert that canceling an order with an invalid ID throws a NotFoundException
		assertThrows(NotFoundException.class, () -> tradeService.cancelOrder(cancelOrder));
	}
}
