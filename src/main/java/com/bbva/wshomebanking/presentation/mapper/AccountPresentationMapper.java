package com.bbva.wshomebanking.presentation.mapper;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.presentation.request.account.AccountCreateRequest;
import com.bbva.wshomebanking.presentation.response.account.AccountFindResponse;
import com.bbva.wshomebanking.presentation.response.account.AccountResponse;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountPresentationMapper {

    public AccountResponse domainToResponse(Account account) {
        List<Client> clientList = new ArrayList<>();
        for (ClientAccount clientAccount : account.getClients()) {
            clientList.add(clientAccount.getClient());
        }

        return AccountResponse.builder()
                .accountNumber(account.getId())
                .currency(account.getCurrency())
                .balance(BigDecimal.ZERO)
                .clients(clientList)
                .active(account.isActive())
                .build();
    }

    public Account requestToDomain(AccountCreateRequest request) {
        return Account.builder()
                .currency(request.getCurrency())
                .balance(BigDecimal.ZERO)
                .build();
    }

    public AccountFindResponse findOneToResponse(Account account) {
        List<Client> clientList = new ArrayList<>();
        List<ClientAccount> clientAccountList = account.getClients();

        for (ClientAccount clientAccount :
                clientAccountList) {
            Client client = clientAccount.getClient();
            client.setAccounts(null);
            clientList.add(client);
        }

        return AccountFindResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .clientList(clientList)
                .currency(account.getCurrency())
                .build();
    }

    /*public Account clientAndCurrencyToDomain(Client client, String currency) {
        ArrayList<Client> clients = new ArrayList<Client>();
        clients.add(client);

        return Account.builder()
                .currency(currency)
                .balance(BigDecimal.ZERO)
                .clients(clients)
                .build();
    }*/

}
