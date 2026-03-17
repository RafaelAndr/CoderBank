package br.com.coderbank.customerportal.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccountRequestDto(
        @NotNull(message = "Required field")
        UUID customerId
) {
}
