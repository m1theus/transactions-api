package dev.mmartins.transactionapi.domain.entity;

import lombok.Getter;

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
        return OperationType.values()[operationId.intValue()];
    }
}
