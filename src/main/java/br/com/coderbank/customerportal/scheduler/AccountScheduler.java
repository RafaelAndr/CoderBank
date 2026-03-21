package br.com.coderbank.customerportal.scheduler;

import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import br.com.coderbank.customerportal.entity.PendingAccount;
import br.com.coderbank.customerportal.enuns.AccountStatus;
import br.com.coderbank.customerportal.service.AccountIntegrationService;
import br.com.coderbank.customerportal.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class AccountScheduler {

    private final CustomerService customerService;
    private final AccountIntegrationService accountIntegrationService;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void processPendingAccounts(){
        List<PendingAccount> pendingAccounts = customerService.getPendingAccounts();

        for (PendingAccount pending : pendingAccounts) {
            CreateAccountRequestDto createAccountRequestDto = new CreateAccountRequestDto(pending.getCustomerId());

            accountIntegrationService.createAccount(createAccountRequestDto);
            pending.setRetryCount(pending.getRetryCount() + 1);
            if(pending.getRetryCount() >= 3){
                pending.setAccountStatus(AccountStatus.FAILED);
            }
        }
    }
}
