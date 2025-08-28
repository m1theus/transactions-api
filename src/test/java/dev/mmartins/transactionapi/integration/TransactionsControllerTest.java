package dev.mmartins.transactionapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mmartins.transactionapi.application.exception.AccountNotFoundException;
import dev.mmartins.transactionapi.application.usecase.CreateTransactionUseCase;
import dev.mmartins.transactionapi.domain.entity.Account;
import dev.mmartins.transactionapi.domain.entity.OperationType;
import dev.mmartins.transactionapi.domain.entity.Transaction;
import dev.mmartins.transactionapi.entrypoint.rest.CreateTransactionRequest;
import dev.mmartins.transactionapi.entrypoint.rest.TransactionResponse;
import dev.mmartins.transactionapi.entrypoint.rest.TransactionsController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateTransactionUseCase createTransactionUseCase = mock(CreateTransactionUseCase.class);

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CreateTransactionUseCase createTransactionUseCase() {
            return mock(CreateTransactionUseCase.class);
        }
    }

    @Autowired
    public void setUseCases(
            CreateTransactionUseCase create
    ) {
        this.createTransactionUseCase = create;
    }

    @AfterEach
    void resetMocks() {
        Mockito.reset(createTransactionUseCase);
    }

    @Test
    void shouldCreateATransaction() throws Exception {
        var request = new CreateTransactionRequest(1L, OperationType.NormalPurchase.getId(), BigDecimal.valueOf(2.22));
        var account = new Account(1L, "12345678900");
        var transaction = new Transaction(1L, account, request.amount(), OperationType.NormalPurchase, LocalDateTime.now());
        when(createTransactionUseCase.execute(request)).thenReturn(new TransactionResponse(transaction));

        mockMvc.perform(post("/transactions")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value("1"))
                .andExpect(jsonPath("$.account_id").value(account.getId()))
                .andExpect(jsonPath("$.amount").value(request.amount()))
                .andExpect(jsonPath("$.operation_type_id").value(request.operationType()))
                .andExpect(jsonPath("$.event_date").isNotEmpty());
    }

    @Test
    void shouldNotCreateATransactionIfAccountDoesNotExist() throws Exception {
        var request = new CreateTransactionRequest(1L, OperationType.NormalPurchase.getId(), BigDecimal.valueOf(2.22));
        Mockito.doThrow(new AccountNotFoundException(1L)).when(createTransactionUseCase).execute(Mockito.any());

        mockMvc.perform(post("/transactions")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.message").value("Account with id 1 not found"));
    }
}
