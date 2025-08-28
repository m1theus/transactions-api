package dev.mmartins.transactionapi.application.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(final Long accountId) {
        super("Account with id " + accountId + " not found");
    }

    public AccountNotFoundException() {
        super("Account not found");
    }
}
