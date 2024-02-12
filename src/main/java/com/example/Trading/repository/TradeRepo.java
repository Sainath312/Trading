package com.example.Trading.repository;

import com.example.Trading.entity.Trade;
import com.example.Trading.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepo extends JpaRepository<Trade,Long> {
    List<Trade> findByUserTradeIdAndOrderType(UserInfo currentUserInfo, String buy);

    List<Trade> findByUserTradeId(UserInfo currentUserInfo);
}
