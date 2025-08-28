package dev.mmartins.transactionapi.entrypoint.rest;

import dev.mmartins.transactionapi.application.usecase.CreateTransactionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionsController(final CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody CreateTransactionRequest transaction) {
        var response = createTransactionUseCase.execute(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
