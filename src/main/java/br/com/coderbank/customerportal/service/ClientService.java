package br.com.coderbank.customerportal.service;

import br.com.coderbank.customerportal.dto.request.ClientRequestDto;
import br.com.coderbank.customerportal.dto.response.ClientResponseDto;
import br.com.coderbank.customerportal.entity.Client;
import br.com.coderbank.customerportal.enuns.Status;
import br.com.coderbank.customerportal.exception.ClientAlreadyExistsException;
import br.com.coderbank.customerportal.exception.DuplicatedEmailException;
import br.com.coderbank.customerportal.mapper.ClientMapper;
import br.com.coderbank.customerportal.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientMapper mapper;

    public ClientResponseDto save(final ClientRequestDto clientRequestDto){

        checkDuplicatedCpf(clientRequestDto.cpf());
        checkDuplicatedEmail(clientRequestDto.email());

        var clientEntity = mapper.toEntity(clientRequestDto);

        clientEntity.setStatus(Status.ACTIVE);

        repository.save(clientEntity);

        return mapper.toDto(clientEntity);
    }

    private void checkDuplicatedCpf(String cpf){

        if (repository.existsByCpf(cpf)) {
            throw new ClientAlreadyExistsException("Client with cpf " + cpf + " already exists");
        }
    }

    private void checkDuplicatedEmail(String email){

        if (repository.existsByEmail(email)) {
            throw new DuplicatedEmailException("Email " + email + " is in use");
        }
    }
}
