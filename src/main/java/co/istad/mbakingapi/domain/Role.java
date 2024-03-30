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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
   private List<Authority> authorities;


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(unique = true,nullable = false,length = 100)
//    private String name;
//    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
//    private List<User> user;

}
