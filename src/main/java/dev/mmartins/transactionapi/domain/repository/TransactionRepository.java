package dev.mmartins.transactionapi.domain.repository;

import dev.mmartins.transactionapi.domain.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    Transaction create(final Transaction transaction);

    List<Transaction> findPreviousTransactions(final Long accountId);

    void saveAll(final List<Transaction> modifiedTransactions);
}
