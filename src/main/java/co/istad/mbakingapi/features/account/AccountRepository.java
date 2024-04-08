package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    //Account findByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
    @Modifying
    @Query("""
            UPDATE Account AS a SET a.isHidden = TRUE  WHERE a.actNo = ?1
            """)
    void hideAccountByActNo(String actNo);
    boolean existsByActNo(String actNo);
}
