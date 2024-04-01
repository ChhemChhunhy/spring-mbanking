package co.istad.mbakingapi.features.accountType;

import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAllAccountTypes();
    List<AccountTypeResponse> findLists();
    AccountTypeResponse findAccountTypeByAlias(String alias);
    AccountTypeResponse findByAlias(String alias);

}
