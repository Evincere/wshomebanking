package com.bbva.wshomebanking.domain.models.transaction;

import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.utilities.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
public class Extraction extends Transaction {

    public Extraction(ClientAccount targetAccount, BigDecimal amount){
        this.setTransactionType(TransactionTypes.DEPOSIT);
        this.setAccount(targetAccount);
        this.setAmount(amount);
    }
    @Override
    public boolean isValid() {
        BigDecimal balance = this.getAccount().getAccount().getBalance();
        BigDecimal amount = this.getAmount();
        return amount.compareTo(balance) <= 0;
    }

    @Override
    public void applyFundsMovements() {
        this.getAccount().getAccount().setBalance(this.getAccount().getAccount().getBalance().subtract(this.getAmount()));
    }
}
