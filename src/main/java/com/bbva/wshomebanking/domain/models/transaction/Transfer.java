package com.bbva.wshomebanking.domain.models.transaction;

public class Transfer extends Transaction {


    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void applyFundsMovements() {

    }
}
