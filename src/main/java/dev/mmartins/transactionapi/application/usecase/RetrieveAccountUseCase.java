package dev.mmartins.transactionapi.application.usecase;

import dev.mmartins.transactionapi.application.exception.AccountNotFoundException;
import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.entrypoint.rest.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class RetrieveAccountUseCase {
    private final AccountRepository accountRepository;

    public RetrieveAccountUseCase(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse execute(final Long accountId) {
        var account = accountRepository.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return new AccountResponse(account);
    }
}
