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

    @ManyToOne
    private Account owner;

    @ManyToOne
    private Account transferReceiver; // uses when transaction type is TRANSFER

    private String paymentReceiver;

    private BigDecimal amount;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false, length = 30)
    private String transactionType; // transfer and payment

    private Boolean status; // Pending, Completed, Failed

    private LocalDateTime transactionAt;



//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    // transactions by one user
//    //one user make many transactions
//    //sender
//    //many transactions have one account
//    @ManyToOne
//    private Account sender;
//    //receiver
//    @ManyToOne
//    //many transaction have one account
//    private Account receiver;
//    private BigDecimal amount;
//    private String remark;
//    //isPayment
//    private Boolean isPayment;
//    private LocalDateTime transactionAt;
//    private Boolean isDelete;
}
