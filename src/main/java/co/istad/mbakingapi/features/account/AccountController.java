package co.istad.mbakingapi.features.account;

import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountRenameRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.account.dto.AccountUpdateTransferLimitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping
    Page<AccountResponse> findList(@RequestParam(required = false , defaultValue ="0") int page,
                                   @RequestParam(required = false,defaultValue = "25" )int size){
        return accountService.findList(page,size);
    }
    @PutMapping("/{actNo}/hide")
    void hideAccountByActNo(@PathVariable String actNo) {
        accountService.hideAccount(actNo);
    }
    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo, @Valid @RequestBody AccountRenameRequest accountRenameRequest){
        return accountService.renameByActNo(actNo,accountRenameRequest);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }

    @PutMapping("/{actNo}/updateLimit")
    AccountResponse updateTransferLimit(@PathVariable String actNo, @RequestBody AccountUpdateTransferLimitRequest accountUpdateTransferLimitRequest){
        return accountService.updateTransferLimit(actNo,accountUpdateTransferLimitRequest);
    }

    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo){
       return accountService.findByActNo(actNo);
    }
//    @GetMapping
//    List<AccountResponse> findAllAccounts(){
//        return accountService.findAll();
//    }
}
