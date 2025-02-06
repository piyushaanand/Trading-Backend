package com.engineeringbureau.trading.controller;

import com.engineeringbureau.trading.domain.PaymentMethod;
import com.engineeringbureau.trading.model.PaymentOrder;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.response.PaymentResponse;
import com.engineeringbureau.trading.service.PaymentOrderService;
import com.engineeringbureau.trading.service.UserService;
//import com.razorpay.RazorpayException;
//import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;

        PaymentOrder order = paymentOrderService.createOrder(user, amount, paymentMethod);
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentOrderService.createRazorpayPaymentLink(user, amount, order.getId());
        }else{
            paymentResponse = paymentOrderService.createStripePaymentLink(user, amount, order.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

}
