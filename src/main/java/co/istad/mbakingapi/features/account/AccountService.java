package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;

import java.util.List;

public interface AccountService{
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);
    List<AccountResponse> findAll();
}
