package br.com.coderbank.customerportal.dto.response;

import br.com.coderbank.customerportal.enuns.Status;

import java.util.UUID;

public record ClientListDto(
        UUID id,
        String name,
        Status status
) {
}
