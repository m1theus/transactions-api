package dev.mmartins.transactionapi.domain.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum OperationType {
    NormalPurchase(1L),
    PurchaseWithInstallments(2L),
    Withdraw(3L),
    CreditVoucher(4L);

    private final Long id;

    OperationType(final Long id) {
        this.id = id;
    }

    public static OperationType from(final Long operationId) {
        return Arrays.stream(OperationType.values())
                .filter(x -> Objects.equals(x.getId(), operationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation id: " + operationId));
    }

    public boolean isNegativeAmount() {
        return this == NormalPurchase || this == PurchaseWithInstallments || this == Withdraw;
    }
}
