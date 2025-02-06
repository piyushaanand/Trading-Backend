package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.domain.VerificationType;
import com.engineeringbureau.trading.model.ForgetPasswordToken;
import com.engineeringbureau.trading.model.User;

public interface ForgotPasswordService {
    ForgetPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerificationType verificationType,
                                    String sendTo);
    ForgetPasswordToken findById(String id);

    ForgetPasswordToken findByUser(Long userId);

    void deleteToken(ForgetPasswordToken token);
}
