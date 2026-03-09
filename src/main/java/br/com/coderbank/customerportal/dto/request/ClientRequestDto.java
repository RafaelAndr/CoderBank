package br.com.coderbank.customerportal.dto.request;

import jakarta.transaction.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ClientRequestDto(
        @NotBlank(message = "required field")
        String name,
        @NotBlank(message = "required field")
        String cpf,
        @Email(message = "The field email must be valid")
        String email,
        String addres,
        @Min(value = 18, message = "The minimum age is 18")
        @Max(value = 80, message = "The maximum age is 80")
        Integer age
) {
}
