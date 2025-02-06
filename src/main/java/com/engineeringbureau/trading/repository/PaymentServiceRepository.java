package com.engineeringbureau.trading.repository;

import com.engineeringbureau.trading.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

//PaymentOrderRepo
public interface PaymentServiceRepository extends JpaRepository<PaymentOrder, Long> {

}
