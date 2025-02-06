package com.engineeringbureau.trading.repository;

import com.engineeringbureau.trading.model.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgetPasswordToken, String> {
    ForgetPasswordToken findByUserId(Long userId);
}
