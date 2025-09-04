package dev.mmartins.transactionapi.application.usecase;

import dev.mmartins.transactionapi.domain.entity.OperationType;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DischargeUseCase {
    private final TransactionRepository transactionRepository;

    public DischargeUseCase(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal execute(final Long accountId, final BigDecimal creditAmount) {
        var currentCreditAmount = new AtomicReference<>(creditAmount);
        var previousTransactions = transactionRepository.findPreviousTransactions(accountId);

        var modifiedTransactions = previousTransactions.stream()
                .filter(transaction -> OperationType.NormalPurchase.equals(transaction.getOperation()))
                .map(transaction -> {
                    if (currentCreditAmount.get().compareTo(BigDecimal.ZERO) == 0) {
                        return null;
                    }

                    if (currentCreditAmount.get().compareTo(transaction.getAmount()) > 0) {
                        var diff = transaction.setNewBalance(currentCreditAmount.get());
                        currentCreditAmount.set(diff);
                        return transaction;
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        if (!modifiedTransactions.isEmpty()) {
            transactionRepository.saveAll(modifiedTransactions);
        }

        return currentCreditAmount.get();
    }

}
