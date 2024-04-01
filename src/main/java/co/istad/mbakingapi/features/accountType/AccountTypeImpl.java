package co.istad.mbakingapi.features.accountType;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTypeImpl implements AccountTypeService{
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public List<AccountTypeResponse> findAllAccountTypes() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypeMapper.toListAccountTypes(accountTypes);
    }

    @Override
    public List<AccountTypeResponse> findLists() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypeMapper.toAccountTypesResponseList(accountTypes);
    }

    @Override
    public AccountTypeResponse findAccountTypeByAlias(String alias) {
        AccountType accountType = accountTypeRepository.findByAliasIgnoreCase(alias).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account does not exits with "+alias+" alias"
                )
        );
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    @Override
    public AccountTypeResponse findByAlias(String alias) {
        AccountType accountType = accountTypeRepository.findByAliasIgnoreCase(alias).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account does not exits with "+alias+" alias"
                )
        );
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
}
