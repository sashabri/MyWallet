package com.example.mywallet.service;

import com.example.mywallet.exception.NotEnoughMoneyException;
import com.example.mywallet.exception.WalletNotFoundException;
import com.example.mywallet.model.WalletInfo;
import com.example.mywallet.repository.WalletInfoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.mywallet.controller.ParamsChecker.*;

@Service
public class DefaultWalletService implements WalletService{

    private final WalletInfoRepository walletInfoRepository;
    private static final String DEPOSIT = "DEPOSIT";
    private static final String WITHDRAW = "WITHDRAW";

    private final Set<String> uuidBlocks = Collections.synchronizedSet(new HashSet<>());

    public DefaultWalletService(WalletInfoRepository walletInfoRepository) {
        this.walletInfoRepository = walletInfoRepository;
    }

    @Override
    public void changeBalance(UUID walletId, Integer amount, String operationType) throws WalletNotFoundException, NotEnoughMoneyException {

        String lock = null;
        uuidBlocks.add(walletId.toString());
        for (String uuid: uuidBlocks) {
            if (uuid.equals(walletId.toString())) {
                lock = walletId.toString();
                break;
            }
        }

        synchronized (Objects.requireNonNull(lock)) {
            WalletInfo wallet = walletInfoRepository.getByWalletId(walletId);

            checkShouldBeNotNullWalletInfo(wallet);

            if(operationType.equals(DEPOSIT)) {
                wallet.setBalance(wallet.getBalance() + amount);
            }

            if(operationType.equals(WITHDRAW)) {
                if(wallet.getBalance() - amount < 0) {
                    throw new NotEnoughMoneyException("There are not enough funds in the account");
                }

                wallet.setBalance(wallet.getBalance() - amount);
            }
            walletInfoRepository.saveAndFlush(wallet);
        }
    }

    @Override
    public Integer getBalance(UUID walletId) throws WalletNotFoundException {
        WalletInfo wallet = walletInfoRepository.getByWalletId(walletId);

        checkShouldBeNotNullWalletInfo(wallet);

        return wallet.getBalance();
    }
}