package co.istad.mbakingapi.features.transaction;

import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbakingapi.features.transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    //transfer money
    TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest);
    Page<TransactionResponse> findList(int page, int size,String sort,String transferType);
    //List<TransactionResponse> findAll(String sort,);
}
