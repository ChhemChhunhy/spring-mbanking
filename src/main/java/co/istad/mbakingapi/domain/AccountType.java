package co.istad.mbakingapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false,length = 100)
    private String name;
    @Column(columnDefinition = "TExT")
    private String description;
    private Boolean isDeleted;

    //one account type have many one account
    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;

}
