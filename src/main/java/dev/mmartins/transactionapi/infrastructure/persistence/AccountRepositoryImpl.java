package dev.mmartins.transactionapi.infrastructure.persistence;

import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.dao.JpaAccountRepository;
import dev.mmartins.transactionapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository repository;

    public AccountRepositoryImpl(final JpaAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account create(final Account account) {
        var entity = repository.save(toEntity(account));
        return toDomain(entity);
    }

    @Override
    public Optional<Account> getAccount(final Long accountId) {
        return repository.findById(accountId)
                .map(this::toDomain);
    }

    private Account toDomain(final AccountEntity entity) {
        return Account.from(entity);
    }

    private AccountEntity toEntity(final Account account) {
        return AccountEntity.from(account);
    }
}
