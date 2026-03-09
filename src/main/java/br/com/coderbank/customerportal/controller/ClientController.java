package br.com.coderbank.customerportal.controller;

import br.com.coderbank.customerportal.dto.response.ClientListDto;
import br.com.coderbank.customerportal.dto.response.ClientResponseDto;
import br.com.coderbank.customerportal.dto.request.ClientRequestDto;
import br.com.coderbank.customerportal.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDto> save(@RequestBody @Valid ClientRequestDto clientRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(clientRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<ClientListDto>> getPagedClients(Pageable pageable){
        return ResponseEntity.ok(service.getPagedClients(pageable));
    }
 }