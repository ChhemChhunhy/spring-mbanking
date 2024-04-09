package co.istad.mbakingapi.features.transaction;

import co.istad.mbakingapi.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    //Page<Transaction> findByTransactionType(String transactionType, PageRequest pageRequest);

    Page<Transaction> findByTransactionType(String transactionType, Pageable pageable);
}
