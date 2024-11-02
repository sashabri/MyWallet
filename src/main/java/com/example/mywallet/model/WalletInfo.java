package com.example.mywallet.model;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "wallet_info")
public class WalletInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true, name = "wallet_id")
    private UUID walletId;

    @Column(name = "balance")
    private Integer balance;

    public WalletInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
