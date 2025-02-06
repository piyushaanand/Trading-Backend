package com.engineeringbureau.trading.repository;

import com.engineeringbureau.trading.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoinRepository extends JpaRepository<Coin, String> {
}
