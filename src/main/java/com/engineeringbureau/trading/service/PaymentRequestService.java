package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.model.PaymentDetails;
import com.engineeringbureau.trading.model.User;

public interface PaymentRequestService {

    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUserPaymentDetails(User user);

}
