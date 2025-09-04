package dev.mmartins.transactionapi.unit;

import dev.mmartins.transactionapi.application.exception.AccountNotFoundException;
import dev.mmartins.transactionapi.application.usecase.CreateTransactionUseCase;
import dev.mmartins.transactionapi.application.usecase.DischargeUseCase;
import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.entity.OperationType;
import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import dev.mmartins.transactionapi.domain.repository.TransactionRepository;
import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateTransactionUseCaseTest {
    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private DischargeUseCase dischargeUseCase;

    @BeforeEach
    void setUp() {
        createTransactionUseCase = new CreateTransactionUseCase(accountRepository, transactionRepository, dischargeUseCase);
    }

    @Test
    void shouldCreateTransaction() {
        // given
        var documentNumber = "12345678900";
        var accountId = 1L;
        var operationId = OperationType.NormalPurchase;
        var amount = BigDecimal.valueOf(2.20);
        var account = new Account(accountId, documentNumber);
        var transaction = new Transaction(1L, account, amount, amount, operationId, LocalDateTime.now());
        when(accountRepository.getAccount(any())).thenReturn(Optional.of(new Account(1L, documentNumber)));
        when(transactionRepository.create(any())).thenReturn(transaction);

        // when
        var response = createTransactionUseCase.execute(new CreateTransactionRequest(accountId, operationId.getId(), amount));

        // then
        assertNotNull(response);
        assertThat(response.accountId()).isEqualTo(accountId);
        assertThat(response.operationTypeId()).isEqualTo(operationId.getId());
        assertThat(response.amount()).isEqualTo(amount);
        verify(accountRepository, times(1)).getAccount(accountId);
        verify(transactionRepository, times(1)).create(any());
        verifyNoMoreInteractions(accountRepository, transactionRepository);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        // given
        var documentNumber = "12345678900";
        var accountId = 1L;
        var operationId = OperationType.NormalPurchase;
        var amount = BigDecimal.valueOf(2.20);
        var account = new Account(accountId, documentNumber);
        var transaction = new Transaction(1L, account, amount, amount, operationId, LocalDateTime.now());
        when(transactionRepository.create(any())).thenReturn(transaction);

        // when
        var response = Assertions.assertThrows(AccountNotFoundException.class,
                () -> createTransactionUseCase.execute(new CreateTransactionRequest(accountId, operationId.getId(), amount)));

        // then
        assertNotNull(response);
        verify(accountRepository, times(1)).getAccount(accountId);
        verifyNoMoreInteractions(accountRepository, transactionRepository);
    }
}
