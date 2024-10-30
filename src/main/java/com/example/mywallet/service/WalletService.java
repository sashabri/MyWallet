package com.example.mywallet.service;

import com.example.mywallet.exception.NotEnoughMoneyException;
import com.example.mywallet.exception.WalletNotFoundException;

import java.util.UUID;

public interface WalletService {
    void changeBalance(UUID walletId, Integer amount, String operationType) throws WalletNotFoundException, NotEnoughMoneyException;

    Integer getBalance(UUID walletId) throws WalletNotFoundException;
}
