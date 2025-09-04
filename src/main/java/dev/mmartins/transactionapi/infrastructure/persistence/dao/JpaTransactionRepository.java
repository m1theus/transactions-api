package dev.mmartins.transactionapi.infrastructure.persistence.dao;

import dev.mmartins.transactionapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByAccountIdOrderByEventDateAsc(final Long accountId);
}
