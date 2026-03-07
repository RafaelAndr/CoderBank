package br.com.coderbank.customerportal.repository;

import br.com.coderbank.customerportal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String clientEmail);
}
