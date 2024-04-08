package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountRenameRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo, @Valid @RequestBody AccountRenameRequest accountRenameRequest){
        return accountService.renameByActNo(actNo,accountRenameRequest);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }


    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo){
       return accountService.findByActNo(actNo);
    }
    @GetMapping
    List<AccountResponse> findAllAccounts(){
        return accountService.findAll();
    }
}
