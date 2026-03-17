package br.com.coderbank.customerportal.client;

import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "accounts", url = "http://localhost:8081")
public interface AccountsFeignClient {

    @PostMapping("/v1/accounts")
    void createAccount(@RequestBody CreateAccountRequestDto createAccountRequestDto);
}
