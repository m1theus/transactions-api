package dev.mmartins.transactionapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.mmartins.transactionapi.domain.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        @JsonProperty("transaction_id") Long id,
        @JsonProperty("account_id") Long accountId,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("operation_type_id") Long operationTypeId,
        @JsonProperty("event_date") LocalDateTime eventDate
) {
    public TransactionResponse(final Transaction transaction) {
        this(transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getOperationId(),
                transaction.getEventDate());
    }
}
