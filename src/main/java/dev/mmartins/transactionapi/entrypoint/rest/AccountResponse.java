package dev.mmartins.transactionapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.mmartins.transactionapi.domain.entity.Account;

public record AccountResponse(
        @JsonProperty("account_id") Long accountId,
        @JsonProperty("document_number") String documentNumber
) {
    public AccountResponse(final Account account) {
        this(account.getId(), account.getDocumentNumber());
    }
}
