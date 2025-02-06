package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.domain.PaymentMethod;
import com.engineeringbureau.trading.domain.PaymentOrderStatus;
import com.engineeringbureau.trading.model.PaymentOrder;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.repository.PaymentServiceRepository;
import com.engineeringbureau.trading.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
//import jakarta.mail.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymnetOrderServiceImpl implements PaymentOrderService{


    @Autowired
    private PaymentServiceRepository paymentServiceRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;



    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setMethod(paymentMethod);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        return paymentServiceRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentServiceRepository.findById(id).orElseThrow(
                ()->new Exception("Payment Order not Found")
        );
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if(paymentOrder.getStatus()==null){
            paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        }
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);
                Payment payment = razorpay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status=payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }

                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentServiceRepository.save(paymentOrder);
                return false;

            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentServiceRepository.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {
         Long Amount = amount*100;

         try{
             RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);

             JSONObject paymentLinkRequest = new JSONObject();
             paymentLinkRequest.put("amount",amount);
             paymentLinkRequest.put("currency","INR");

             JSONObject customer = new JSONObject();
             customer.put("name",user.getFullName());
             customer.put("email",user.getEmail());
             paymentLinkRequest.put("customer",customer);

             JSONObject notify = new JSONObject();
             notify.put("email",true);
             paymentLinkRequest.put("notify",notify);

             paymentLinkRequest.put("reminder_enable",true);

             //remainder--change

             paymentLinkRequest.put("callback_url","https://localhost:5173/wallet?order_id="+orderId);
             paymentLinkRequest.put("callback_method","get");

             //GET

             PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

             String paymentLinkId = payment.get("id");
             String paymentLinkUrl = payment.get("short_url");

             PaymentResponse res = new PaymentResponse();
             res.setPayment_url(paymentLinkUrl);

             return res;

         }catch (RazorpayException e){

             System.out.println("Error Creating Payment link : "+e.getMessage());
             throw new RazorpayException(e.getMessage());
         }
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id="+orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount+100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("To up Wallet")
                                        .build()
                                ).build()
                        ).build()

                ).build();
        Session session = Session.create(params);

        System.out.println("Session_____"+session);

        PaymentResponse res = new PaymentResponse();
        res.setPayment_url(session.getUrl());

        return res;
    }
}
