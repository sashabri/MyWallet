package com.example.mywallet.controller;

import com.example.mywallet.controller.entity.DepositOrWithdrawBody;
import com.example.mywallet.exception.InvalidInputDataException;
import com.example.mywallet.exception.WalletNotFoundException;
import com.example.mywallet.model.WalletInfo;

import java.util.UUID;

public class ParamsChecker {
    public static void checkShouldBeNotEmptyStr(String param) throws InvalidInputDataException {
        if (param == null || param.isEmpty()) {
            throw new InvalidInputDataException("Invalid operation type");
        }
    }

    public static void checkShouldBeNotEmptyNumber(Integer numberParam) throws InvalidInputDataException {
        if (numberParam == 0) {
            throw new InvalidInputDataException("Amount is null");
        }
    }

    public static void checkShouldBeNotEmptyWalletId(UUID walletId) throws InvalidInputDataException {
        if (walletId == null) {
            throw new InvalidInputDataException("WalletId is null");
        }
    }

    public static void checkShouldBeNotDepositOrWithdrawBody(DepositOrWithdrawBody body) throws InvalidInputDataException {
        if (body == null) {
            throw new InvalidInputDataException("Input DepositOrWithdrawBody is null");
        }
    }
    public static void checkShouldBeNotNullWalletInfo(WalletInfo walletInfo) throws WalletNotFoundException {
        if (walletInfo == null) {
            throw new WalletNotFoundException("Input walletId not found");
        }
    }
}
