package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountRenameRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.account.dto.AccountUpdateTransferLimitRequest;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService{
    Page<AccountResponse> findList(int page, int size);
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);

    List<AccountResponse> findAll();
    AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest);
    AccountResponse updateTransferLimit(String actNo, AccountUpdateTransferLimitRequest accountUpdateTransferLimitRequest);
    void hideAccount(String actNo);
}
