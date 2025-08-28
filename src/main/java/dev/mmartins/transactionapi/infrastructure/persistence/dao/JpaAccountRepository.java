package dev.mmartins.transactionapi.infrastructure.persistence.dao;

import dev.mmartins.transactionapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {
}
