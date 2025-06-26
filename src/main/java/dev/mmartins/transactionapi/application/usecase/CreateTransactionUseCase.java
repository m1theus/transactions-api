package dev.mmartins.transactionapi.application.usecase;

import dev.mmartins.transactionapi.application.exception.AccountNotFoundException;
import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import dev.mmartins.transactionapi.entrypoint.rest.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateTransactionUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public CreateTransactionUseCase(final AccountRepository accountRepository,
                                    final TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse execute(final CreateTransactionRequest input) {
        var account = accountRepository.getAccount(input.accountId())
                .orElseThrow(() -> new AccountNotFoundException(input.accountId()));

        var transaction = transactionRepository.create(new Transaction(account, input));
        return new TransactionResponse(transaction);
    }
}
