package dev.mmartins.transactionapi.domain.entity;

import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import dev.mmartins.transactionapi.infrastructure.persistence.entity.TransactionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
public class Transaction {
    @Getter
    private Long id;
    @Getter
    private Account account;
    @Getter
    private BigDecimal amount;
    @Getter
    private OperationType operation;
    @Getter
    private LocalDateTime eventDate;

    public Transaction(final Long id,
                       final Account account,
                       final BigDecimal amount,
                       final OperationType operation,
                       final LocalDateTime eventDate) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.operation = operation;
        this.eventDate = eventDate;
    }

    public Transaction(final Account account,
                       final CreateTransactionRequest request) {
        this.account = account;
        this.amount = request.amount();
        this.operation = OperationType.from(request.operationType());
        this.eventDate = LocalDateTime.now();
    }

    public static Transaction from(final TransactionEntity entity) {
        var account = Account.from(entity.getAccount());
        var operation = OperationType.from(entity.getOperationId());
        return new Transaction(entity.getId(),
                account,
                entity.getAmount(),
                operation,
                entity.getEventDate());
    }

    public Long getOperationId() {
        return operation.getId();
    }

    public Long getAccountId() {
        return account.getId();
    }
}
