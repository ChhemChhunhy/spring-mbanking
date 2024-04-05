package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;

public interface AccountService{
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);

    AccountResponse findByAccountNo(String actNo);
}
