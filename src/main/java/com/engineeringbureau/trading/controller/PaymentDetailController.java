package com.engineeringbureau.trading.controller;

//import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.PaymentDetails;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.service.PaymentRequestService;
import com.engineeringbureau.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails
            (
                    @RequestBody PaymentDetails paymentDetailsRequest,
                    @RequestHeader("Authorization") String jwt

            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentRequestService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUserPaymentDetails
            (
                    @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentRequestService.getUserPaymentDetails(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }
}

