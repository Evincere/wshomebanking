package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.transaction.Transaction;
import com.bbva.wshomebanking.presentation.request.transaction.TransactionCreateRequest;
import com.bbva.wshomebanking.utilities.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionPresentationMapper {

    public TransactionResponse domainToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .accountFrom(transaction.getAccount().getAccount().getId())
                .amount(transaction.getAmount())
                .build();
    }

}
