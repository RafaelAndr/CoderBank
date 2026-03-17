package br.com.coderbank.customerportal.service;

import br.com.coderbank.customerportal.client.AccountClientFallback;
import br.com.coderbank.customerportal.client.AccountsClient;
import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountIntegrationService {

    private final AccountsClient accountsClient;
    private final AccountClientFallback accountClientFallback;

    @Retry(name = "AccountServiceRetry")
    @CircuitBreaker(name = "accountServiceCB", fallbackMethod = "fallbackCreateAccount")
    public void createAccount(CreateAccountRequestDto dto) {
        accountsClient.createAccount(dto);
    }

    public void fallbackCreateAccount(
            CreateAccountRequestDto dto,
            Throwable ex) {

        System.out.println("🔥 FALLBACK EXECUTADO");

        accountClientFallback.createAccount(dto);
    }

}
