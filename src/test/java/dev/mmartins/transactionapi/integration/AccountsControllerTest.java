package dev.mmartins.transactionapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mmartins.transactionapi.application.usecase.CreateAccountUseCase;
import dev.mmartins.transactionapi.application.usecase.RetrieveAccountUseCase;
import dev.mmartins.transactionapi.entrypoint.rest.AccountResponse;
import dev.mmartins.transactionapi.entrypoint.rest.AccountsController;
import dev.mmartins.transactionapi.entrypoint.rest.CreateAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsController.class)
public class AccountsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateAccountUseCase createAccountUseCase = mock(CreateAccountUseCase.class);
    private RetrieveAccountUseCase retrieveAccountUseCase = mock(RetrieveAccountUseCase.class);

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CreateAccountUseCase createAccountUseCase() {
            return mock(CreateAccountUseCase.class);
        }

        @Bean
        public RetrieveAccountUseCase retrieveAccountUseCase() {
            return mock(RetrieveAccountUseCase.class);
        }
    }

    @Autowired
    public void setUseCases(
            CreateAccountUseCase create,
            RetrieveAccountUseCase retrieve
    ) {
        this.createAccountUseCase = create;
        this.retrieveAccountUseCase = retrieve;
    }

    @Test
    void shouldCreateAnAccount() throws Exception {
        var request = new CreateAccountRequest("12345678900");
        when(createAccountUseCase.execute(request.documentNumber())).thenReturn(new AccountResponse(1L, request.documentNumber()));

        mockMvc.perform(post("/accounts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value("1"))
                .andExpect(jsonPath("$.document_number").value(request.documentNumber()));
    }

    @Test
    void shouldRetrieveAccount() throws Exception {
        var account = new AccountResponse(1L, "12345678900");
        when(retrieveAccountUseCase.execute(account.accountId())).thenReturn(account);

        mockMvc.perform(get("/accounts/{id}", account.accountId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(account.accountId()))
                .andExpect(jsonPath("$.document_number").value(account.documentNumber()));
    }
}
