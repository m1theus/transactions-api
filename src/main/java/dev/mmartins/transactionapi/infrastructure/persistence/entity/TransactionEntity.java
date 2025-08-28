package dev.mmartins.transactionapi.infrastructure.persistence.entity;

import dev.mmartins.transactionapi.domain.entity.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "operation_type", nullable = false)
    private Long operationId;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    public static TransactionEntity from(final Transaction transaction) {
        var accountEntity = AccountEntity.from(transaction.getAccount());
        return new TransactionEntity(transaction.getId(),
                accountEntity,
                transaction.getAmount(),
                transaction.getOperationId(),
                transaction.getEventDate());
    }
}
