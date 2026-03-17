package br.com.coderbank.customerportal.repository;

import br.com.coderbank.customerportal.entity.PendingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PendingAccountRepository extends JpaRepository<PendingAccount, UUID> {
}
