package dev.mmartins.transactionapi.unit;

import dev.mmartins.transactionapi.application.usecase.CreateAccountUseCase;
import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateAccountUseCaseTest {
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        createAccountUseCase = new CreateAccountUseCase(accountRepository);
    }

    @Test
    void shouldCreateAccount() {
        // given
        var documentNumber = "12345678900";
        when(accountRepository.create(any())).thenReturn(new Account(1L, documentNumber));

        // when
        var response = createAccountUseCase.execute(documentNumber);

        // then
        assertNotNull(response);
        assertThat(response.documentNumber()).isEqualTo(documentNumber);
        verify(accountRepository).create(any());
        verifyNoMoreInteractions(accountRepository);
    }
}
