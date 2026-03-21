package br.com.coderbank.customerportal.controller;

import br.com.coderbank.customerportal.dto.response.CustomerListDto;
import br.com.coderbank.customerportal.dto.response.CustomerResponseDto;
import br.com.coderbank.customerportal.dto.request.CustomerRequestDto;
import br.com.coderbank.customerportal.entity.PendingAccount;
import br.com.coderbank.customerportal.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class ClientController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> save(@RequestBody @Valid CustomerRequestDto customerRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(customerRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerListDto>> getPagedClients(Pageable pageable){
        return ResponseEntity.ok(service.getPagedClients(pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public List<PendingAccount> getPendingAccounts(){
        return service.getPendingAccounts();
    }
 }