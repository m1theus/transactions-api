package dev.mmartins.transactionapi.entrypoint.rest;

import dev.mmartins.transactionapi.application.usecase.CreateAccountUseCase;
import dev.mmartins.transactionapi.application.usecase.RetrieveAccountUseCase;
import dev.mmartins.transactionapi.domain.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final CreateAccountUseCase createAccountUseCase;
    private final RetrieveAccountUseCase retrieveAccountUseCase;

    public AccountsController(final CreateAccountUseCase createAccountUseCase,
                              final RetrieveAccountUseCase retrieveAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.retrieveAccountUseCase = retrieveAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
        var response = createAccountUseCase.execute(request.documentNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(retrieveAccountUseCase.execute(id));
    }
}
