package co.istad.mbakingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 9)
    private String actNo;

    @Column(nullable = false, length = 100)
    private String actName;

    @Column(length = 100)
    private String alias;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal transferLimit;

    // Account has a type
    @ManyToOne(cascade = CascadeType.PERSIST)
    private AccountType accountType;

    @OneToMany(mappedBy = "account")
    private List<UserAccount> userAccountList;

    @OneToOne
    private Card card;

    private Boolean isHidden; // uses to hide account on mobile app

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(unique = true,nullable = false,length = 9)
//    private String actNo;
//    @Column(unique = true,nullable = false,length = 100)
//    private String actName;
//
//    private BigDecimal transferLimit;
//    //many accounts have one account type
//    @ManyToOne
//    private AccountType accountType;
//    //account has one user
//    //@ManyToOne
//    @OneToMany(mappedBy = "account")
//    private List<UserAccount> userAccountList;
//
//    @OneToOne
//    private Card card;


    //@JoinTable(name="user_accounts")

    //private User user;
}
