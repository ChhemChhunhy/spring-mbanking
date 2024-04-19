package co.istad.mbakingapi.features.transaction.dto;

import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.account.dto.AccountSnippetAccountResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        AccountSnippetAccountResponse owner,
        AccountResponse transferReceiver,
        BigDecimal amount,
        String remark,
        String transactionType,
        Boolean status,
        LocalDateTime transactionAt
) {
}
