package br.com.coderbank.customerportal.service;

import br.com.coderbank.customerportal.client.AccountClientFallback;
import br.com.coderbank.customerportal.client.AccountsClient;
import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import br.com.coderbank.customerportal.entity.PendingAccount;
import br.com.coderbank.customerportal.enuns.AccountStatus;
import br.com.coderbank.customerportal.repository.PendingAccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountIntegrationService {

    private final AccountsClient accountsClient;
    private final AccountClientFallback accountClientFallback;
    private final PendingAccountRepository pendingAccountRepository;

    @Retry(name = "AccountServiceRetry")
    @CircuitBreaker(name = "accountServiceCB", fallbackMethod = "fallbackCreateAccount")
    public void createAccount(CreateAccountRequestDto dto) {
        accountsClient.createAccount(dto);
        changePendingAccountStatus(dto.customerId());
    }

    public void fallbackCreateAccount(
            CreateAccountRequestDto dto,
            Exception ex) {

        System.out.println("🔥 FALLBACK EXECUTADO");

        accountClientFallback.createAccount(dto);
    }

    public void changePendingAccountStatus(UUID customerId){
        PendingAccount pendingAccount = pendingAccountRepository.findByCustomerId(customerId);

        if (pendingAccount != null) {
            pendingAccount.setAccountStatus(AccountStatus.SUCCESS);
        }
    }
}
