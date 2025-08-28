package dev.mmartins.transactionapi.infrastructure.persistence;

import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.dao.JpaTransactionRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final JpaTransactionRepository repository;

    public TransactionRepositoryImpl(final JpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction create(final Transaction transaction) {
        var entity = repository.save(toEntity(transaction));
        return toDomain(entity);
    }

    private Transaction toDomain(final TransactionEntity entity) {
        return Transaction.from(entity);
    }

    private TransactionEntity toEntity(final Transaction transaction) {
        return TransactionEntity.from(transaction);
    }
}
