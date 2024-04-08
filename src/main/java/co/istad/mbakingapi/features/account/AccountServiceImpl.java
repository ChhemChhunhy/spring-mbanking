package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.domain.Account;
import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.domain.UserAccount;
import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountRenameRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.accountType.AccountTypeRepository;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.user.UserRepository;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import co.istad.mbakingapi.features.user.dto.UserDetailResponse;
import co.istad.mbakingapi.features.user.dto.UserResponse;
import co.istad.mbakingapi.mapper.AccountMapper;
import co.istad.mbakingapi.mapper.AccountTypeMapper;
import co.istad.mbakingapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final AccountTypeMapper accountTypeMapper;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;
    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {
        // check account type
        AccountType accountType = accountTypeRepository.findByAliasIgnoreCase(accountCreateRequest.accountTypeAlias())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Invalid account type"));

        // check user by UUID
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found"));

        // map account dto to account entity
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo("102345678");
        account.setTransferLimit(BigDecimal.valueOf(50000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreateAt(LocalDateTime.now());

        userAccountRepository.save(userAccount);


    }



    //    @Override
//    public AccountResponse findByActNo(String actNo) {
//        Account account = accountRepository.findByActNo(actNo).orElseThrow(
//                ()-> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Account not found"
//                )
//        );
//
//        AccountTypeResponse accountType = accountTypeMapper.toAccountTypeResponse(account.getAccountType());
//        UserResponse user = userMapper.toUserResponse(account.getUserAccountList().get(0).getUser());
//        return new AccountResponse(account.getAlias(),account.getActName(),account.getTransferLimit(),account.getBalance(),accountType,user);
//    }
    @Override
    public AccountResponse findByActNo(String actNo) {
        Account account =accountRepository.findByActNo(actNo).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account no is valid"
                )
        );

        return accountMapper.toAccountResponse(account);
    }

    @Override
    public List<AccountResponse> findAll() {
        List<Account> account = accountRepository.findAll();
        return accountMapper.toAccountResponseList(account);
    }

    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest) {

        //check actNo if it exists
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account no is valid"
                )
        );
        //check old alias and new alias
        if (account.getAlias().equals(accountRenameRequest.newName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "New alias cannot be the same as the old one");
        }
        account.setAlias(accountRenameRequest.newName());
         account=  accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }


}
