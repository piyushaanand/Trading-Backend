package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.Withdrawl;

import java.util.List;

public interface WithdrawlService {

    Withdrawl requestWithdrawl(Long amount, User user);

    Withdrawl proceedWithWithdraawl(Long withdrawlId, boolean accept) throws Exception;

    List<Withdrawl> getUserWithdrawlHistory(User user);

    List<Withdrawl> getAllWithdrawRequest();
}
