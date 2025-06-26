package dev.mmartins.transactionapi.unit;

import dev.mmartins.transactionapi.application.usecase.CreateAccountUseCase;
import dev.mmartins.transactionapi.application.usecase.RetrieveAccountUseCase;
import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RetrieveAccountUseCaseTest {
    private RetrieveAccountUseCase retrieveAccountUseCase;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        retrieveAccountUseCase = new RetrieveAccountUseCase(accountRepository);
    }

    @Test
    void shouldRetrieveTheAccountInformation() {
        // given
        var accountId = 1L;
        var documentNumber = "12345678900";
        when(accountRepository.getAccount(accountId)).thenReturn(Optional.of(new Account(1L, documentNumber)));

        // when
        var response = retrieveAccountUseCase.execute(accountId);

        // then
        assertNotNull(response);
        assertThat(response.accountId()).isEqualTo(accountId);
        assertThat(response.documentNumber()).isEqualTo(documentNumber);
        verify(accountRepository, times(1)).getAccount(accountId);
        verifyNoMoreInteractions(accountRepository);
    }
}
