package co.istad.mbakingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String content;

    private LocalDate transactionAt;
    @ManyToOne
    private Transaction transaction;
    @ManyToOne
    private User user;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
}
