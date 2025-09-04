package dev.mmartins.transactionapi.application.usecase;

import dev.mmartins.transactionapi.application.exception.AccountNotFoundException;
import dev.mmartins.transactionapi.domain.entity.OperationType;
import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import dev.mmartins.transactionapi.entrypoint.rest.TransactionResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreateTransactionUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final DischargeUseCase dischargeUseCase;

    public CreateTransactionUseCase(final AccountRepository accountRepository,
                                    final TransactionRepository transactionRepository,
                                    final DischargeUseCase dischargeUseCase) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.dischargeUseCase = dischargeUseCase;
    }

    public TransactionResponse execute(final CreateTransactionRequest input) {
        var account = accountRepository.getAccount(input.accountId())
                .orElseThrow(() -> new AccountNotFoundException(input.accountId()));

        var creditValue = BigDecimal.ZERO;
        if (OperationType.CreditVoucher.getId().equals(input.operationType())) {
            creditValue = dischargeUseCase.execute(account.getId(), input.amount());
        }

        Transaction newTransaction = new Transaction(account, input);
        newTransaction.setNewBalance(creditValue);
        var transaction = transactionRepository.create(newTransaction);


        return new TransactionResponse(transaction);
    }
}
