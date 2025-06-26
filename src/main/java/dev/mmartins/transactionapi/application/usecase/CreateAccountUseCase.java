package dev.mmartins.transactionapi.application.usecase;

import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.entrypoint.rest.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse execute(final String documentNumber) {
        var account = accountRepository.create(new Account(documentNumber));
        return new AccountResponse(account);
    }
}
