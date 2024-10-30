package com.example.mywallet.controller.entity;

import java.util.UUID;

public class DepositOrWithdrawBody {
    private UUID walletId;
    private String operationType;
    private Integer amount;

    public DepositOrWithdrawBody(UUID walletId, String operationType, Integer amount) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public String getOperationType() {
        return operationType;
    }

    public Integer getAmount() {
        return amount;
    }
}
