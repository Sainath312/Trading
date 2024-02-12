package com.example.Trading.repository;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.entity.Orders;
import com.example.Trading.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {
    @Query(StringConstants.FindBuyOrdersQuery)
    List<Orders> findBuyOrders();

    @Query(StringConstants.FindSellOrdersQuery)
    List<Orders> findSellOrders();

    List<Orders> findByUserOrdersIdAndOrderType(UserInfo userInfo, String orderType);

    List<Orders> findByUserOrdersId(UserInfo currentUserInfo);


}
