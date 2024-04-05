package co.istad.mbakingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    //many user_accounts to one user
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    //many user_accounts to one account
    private Account account;
    private Boolean isDeleted;
    private Boolean isBlocked;
    private LocalDateTime createAt;
}
