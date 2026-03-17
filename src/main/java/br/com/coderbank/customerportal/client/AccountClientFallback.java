package br.com.coderbank.customerportal.client;

import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import br.com.coderbank.customerportal.entity.PendingAccount;
import br.com.coderbank.customerportal.enuns.AccountStatus;
import br.com.coderbank.customerportal.repository.PendingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountClientFallback implements AccountsClient {

    private final PendingAccountRepository pendingAccountRepository;

    @Override
    public void createAccount(CreateAccountRequestDto createAccountRequestDto) {

        PendingAccount pendingAccount = new PendingAccount();
        pendingAccount.setCustomerId(createAccountRequestDto.customerId());
        pendingAccount.setRetryCount(0);
        pendingAccount.setAccountStatus(AccountStatus.PENDING);
        pendingAccount.setLastTimeAttempt(LocalDateTime.now());

        pendingAccountRepository.save(pendingAccount);
    }
}
