package dev.mmartins.transactionapi.domain.repository;

import dev.mmartins.transactionapi.domain.entity.Account;

import java.util.Optional;

public interface AccountRepository {
    Account create(final Account account);

    Optional<Account> getAccount(final Long accountId);
}
