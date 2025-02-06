package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.model.Order;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
}
