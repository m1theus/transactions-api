package dev.mmartins.transactionapi.infrastructure.persistence;

import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.dao.JpaTransactionRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Transaction> findPreviousTransactions(final Long accountId) {
        return repository.findAllByAccountIdOrderByEventDateAsc(accountId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void saveAll(final List<Transaction> transactions) {
        var entities = transactions
                .stream()
                .map(this::toEntity)
                .toList();

        repository.saveAll(entities);
    }

    private Transaction toDomain(final TransactionEntity entity) {
        return Transaction.from(entity);
    }

    private TransactionEntity toEntity(final Transaction transaction) {
        return TransactionEntity.from(transaction);
    }
}
