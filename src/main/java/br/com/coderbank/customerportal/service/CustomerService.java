package br.com.coderbank.customerportal.service;

import br.com.coderbank.customerportal.dto.request.CreateAccountRequestDto;
import br.com.coderbank.customerportal.dto.request.CustomerRequestDto;
import br.com.coderbank.customerportal.dto.response.CustomerListDto;
import br.com.coderbank.customerportal.dto.response.CustomerResponseDto;
import br.com.coderbank.customerportal.entity.Customer;
import br.com.coderbank.customerportal.entity.PendingAccount;
import br.com.coderbank.customerportal.enuns.AccountStatus;
import br.com.coderbank.customerportal.enuns.Status;
import br.com.coderbank.customerportal.exception.CustomerAlreadyExistsException;
import br.com.coderbank.customerportal.exception.DuplicatedEmailException;
import br.com.coderbank.customerportal.mapper.CustomerMapper;
import br.com.coderbank.customerportal.repository.CustomerRepository;
import br.com.coderbank.customerportal.repository.PendingAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final AccountIntegrationService accountIntegrationService;
    private final PendingAccountRepository pendingAccountRepository;

    public CustomerResponseDto save(final CustomerRequestDto customerRequestDto){

        checkDuplicatedCpf(customerRequestDto.cpf());
        checkDuplicatedEmail(customerRequestDto.email());

        Customer customerEntity = mapper.toEntity(customerRequestDto);

        customerEntity.setStatus(Status.ACTIVE);

        Customer savedCustomer = repository.save(customerEntity);

        CreateAccountRequestDto createAccountRequestDto = mapper.toCreateAccountDto(savedCustomer);

        accountIntegrationService.createAccount(createAccountRequestDto);

        return mapper.toDto(savedCustomer);
    }

    private void checkDuplicatedCpf(String cpf){

        if (repository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException("Client with cpf " + cpf + " already exists");
        }
    }

    private void checkDuplicatedEmail(String email){

        if (repository.existsByEmail(email)) {
            throw new DuplicatedEmailException("Email " + email + " is in use");
        }
    }

    public Page<CustomerListDto> getPagedClients(Pageable pageable) {

        return repository
                .findAll(pageable)
                .map(mapper::toPagedDto);
    }

    public List<PendingAccount> getPendingAccounts(){
        return pendingAccountRepository.findByAccountStatus(AccountStatus.PENDING);
    }

    public void delete(UUID id){
        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Customer not found");
        }
        repository.deleteById(id);
    }

}
