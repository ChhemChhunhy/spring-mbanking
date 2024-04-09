package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.Transaction;
import co.istad.mbakingapi.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbakingapi.features.transaction.dto.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = AccountMapper.class)
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);
    Transaction fromTransactCreateRequest(TransactionCreateRequest transactionCreateRequest);
}
