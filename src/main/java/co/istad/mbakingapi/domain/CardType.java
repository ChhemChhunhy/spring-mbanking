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
@Table(name = "card_types")
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    private Boolean isDeleted;
    @OneToMany(mappedBy = "cardType")
    private List<Card> cards;


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(unique = true,nullable = false,length = 100)
//    private String name;
//    private Boolean isDeleted;
//
//    //one account type have many one account
//    @OneToMany(mappedBy = "cardType")
//    private List<Card> cards;
}
