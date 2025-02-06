package com.engineeringbureau.trading.controller;

import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.Wallet;
//import com.engineeringbureau.trading.model.WalletTransaction;
import com.engineeringbureau.trading.model.Withdrawl;
import com.engineeringbureau.trading.service.UserService;
import com.engineeringbureau.trading.service.WalletService;
import com.engineeringbureau.trading.service.WithdrawlService;
//import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/withdrawl")
public class WithdrawlController {

    @Autowired
    private WithdrawlService withdrawlService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/withdrawl/{amount}")
    public ResponseEntity<?> withdrawRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawl withdrawl = withdrawlService.requestWithdrawl(amount, user);
        walletService.addBalance(userWallet, -withdrawl.getAmount());
        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawl/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawl(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawl withdrawl = withdrawlService.proceedWithWithdraawl(id, accept);
        Wallet userWallet = walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(userWallet, withdrawl.getAmount());

        }
        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawl")
    public ResponseEntity<List<Withdrawl>> getWithdrawlHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawl> withdrawl = withdrawlService.getUserWithdrawlHistory(user);

        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawl")
    public ResponseEntity<List<Withdrawl>> getAllWithdrawlRequest(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawl> withdrawl = withdrawlService.getAllWithdrawRequest();
        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }
}
