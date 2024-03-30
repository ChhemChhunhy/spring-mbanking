package co.istad.mbakingapi.features.accountType;

import co.istad.mbakingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {
    Optional<AccountType> findByAliasIgnoreCase(String alias);
}
