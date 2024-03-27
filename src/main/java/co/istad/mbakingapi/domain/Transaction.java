package co.istad.mbakingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // transactions by one user
    //one user make many transactions
    //sender
    //many transactions have one account
    @ManyToOne
    private Account sender;
    //receiver
    @ManyToOne
    //many transaction have one account
    private Account receiver;
    private BigDecimal amount;
    private String remark;
    //isPayment
    private Boolean isPayment;
    private LocalDateTime transactionAt;
    private Boolean isDelete;
}
