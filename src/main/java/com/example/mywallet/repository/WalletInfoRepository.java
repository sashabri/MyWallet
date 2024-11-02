package com.example.mywallet.repository;

import com.example.mywallet.exception.WalletNotFoundException;
import com.example.mywallet.model.WalletInfo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface WalletInfoRepository extends JpaRepository<WalletInfo, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from WalletInfo u where u.walletId = :wallet_id")
    public WalletInfo getByWalletId(@Param("wallet_id") UUID walletId) throws WalletNotFoundException;
}
