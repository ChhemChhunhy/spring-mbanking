package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    //Account findByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
}
