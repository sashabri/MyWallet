package com.example.mywallet.controller;

import com.example.mywallet.controller.entity.DepositOrWithdrawBody;
import com.example.mywallet.exception.InvalidInputDataException;
import com.example.mywallet.exception.NotEnoughMoneyException;
import com.example.mywallet.exception.WalletNotFoundException;
import com.example.mywallet.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.mywallet.controller.ParamsChecker.*;

@RestController
@RequestMapping("/")
public class Controller {
    private final WalletService walletService;

    public Controller(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("api/v1/wallet")
    public void doDepositOrWithdraw(@RequestBody DepositOrWithdrawBody depositOrWithdrawBody)
            throws InvalidInputDataException, NotEnoughMoneyException, WalletNotFoundException {
       Integer amount = depositOrWithdrawBody.getAmount();
       UUID walletId =depositOrWithdrawBody.getWalletId();
       String operationType = depositOrWithdrawBody.getOperationType().toUpperCase();

        checkShouldBeNotDepositOrWithdrawBody(depositOrWithdrawBody);
        checkShouldBeNotEmptyNumber(amount);
        checkShouldBeNotEmptyStr(operationType);
        checkShouldBeNotEmptyWalletId(walletId);

        walletService.changeBalance(walletId, amount, operationType);
    }

    @GetMapping("api/v1/wallets/{WALLET_UUID}")
    public Integer getBalanceWallet(@PathVariable UUID WALLET_UUID)
            throws InvalidInputDataException, WalletNotFoundException {
        checkShouldBeNotEmptyWalletId(WALLET_UUID);

        return walletService.getBalance(WALLET_UUID);
    }
}
