package dev.mmartins.transactionapi.domain.entity;

import dev.mmartins.transactionapi.infrastructure.persistence.entity.AccountEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
public class Account {
    @Getter
    private Long id;
    @Getter
    private String documentNumber;

    public Account(final Long id, final String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public Account(final String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public static Account from(final AccountEntity entity) {
        return new Account(entity.getId(), entity.getDocumentNumber());
    }
}
