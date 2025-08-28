package dev.mmartins.transactionapi.domain.repository;

import dev.mmartins.transactionapi.domain.entity.Transaction;

public interface TransactionRepository {
    Transaction create(final Transaction transaction);
}
