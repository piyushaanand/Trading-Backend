package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.domain.VerificationType;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);

}
