package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    List<AccountTypeResponse> toListAccountTypes(List<AccountType> accountTypes);

    AccountTypeResponse toAccountTypeResponse(AccountType accountType);

}
