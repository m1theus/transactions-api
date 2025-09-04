package dev.mmartins.transactionapi.domain.entity;

import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import dev.mmartins.transactionapi.infrastructure.persistence.entity.TransactionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Transaction {
    @Getter
    private Long id;
    @Getter
    private Account account;
    @Getter
    private BigDecimal amount;
    @Getter
    private BigDecimal balance;
    @Getter
    private OperationType operation;
    @Getter
    private LocalDateTime eventDate;

    public Transaction(final Long id,
                       final Account account,
                       final BigDecimal amount,
                       final BigDecimal balance,
                       final OperationType operation,
                       final LocalDateTime eventDate) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.balance = balance;
        this.operation = operation;
        this.eventDate = eventDate;
    }

    public Transaction(final Account account,
                       final CreateTransactionRequest request) {
        var operation = OperationType.from(request.operationType());
        var amountValue = operation.isNegativeAmount() ? request.amount().negate() : request.amount();
        this.account = account;
        this.amount = amountValue;
        this.balance = amountValue;
        this.operation = operation;
        this.eventDate = LocalDateTime.now();
    }

    public static Transaction from(final TransactionEntity entity) {
        var account = Account.from(entity.getAccount());
        var operation = OperationType.from(entity.getOperationId());
        return new Transaction(entity.getId(),
                account,
                entity.getAmount(),
                entity.getBalance(),
                operation,
                entity.getEventDate());
    }

    public Long getOperationId() {
        return operation.getId();
    }

    public Long getAccountId() {
        return account.getId();
    }

    public BigDecimal setNewBalance(final BigDecimal creditAmount) {
        var diff = this.balance.add(creditAmount);
        if (diff.compareTo(BigDecimal.ZERO) >= 0) {
            this.balance = BigDecimal.ZERO;
        } else {
            this.balance = diff;
        }
        return diff;
    }

}
