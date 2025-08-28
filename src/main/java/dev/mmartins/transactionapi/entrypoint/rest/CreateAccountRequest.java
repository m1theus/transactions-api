package dev.mmartins.transactionapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAccountRequest(
        @JsonProperty("document_number") String documentNumber
) {
}
