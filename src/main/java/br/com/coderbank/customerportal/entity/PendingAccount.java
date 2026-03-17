package br.com.coderbank.customerportal.entity;

import br.com.coderbank.customerportal.enuns.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "CB_PENDING_ACCOUNT")
public class PendingAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "retry_count")
    private int retryCount;

    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Column(name = "last_time_attempt")
    private LocalDateTime lastTimeAttempt;

    @Column(name = "error_message")
    private String errorMessage;
}
