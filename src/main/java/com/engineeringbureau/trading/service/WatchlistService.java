package com.engineeringbureau.trading.service;

import com.engineeringbureau.trading.model.Coin;
import com.engineeringbureau.trading.model.User;
import com.engineeringbureau.trading.model.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;

}
