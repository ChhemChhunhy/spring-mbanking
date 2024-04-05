package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.Account;
import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);
    AccountResponse toAccountResponse(Account account);
}
