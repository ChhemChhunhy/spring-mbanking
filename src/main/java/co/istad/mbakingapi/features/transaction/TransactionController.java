package co.istad.mbakingapi.features.transaction;

import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbakingapi.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    TransactionResponse transfer(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest){
        return transactionService.transfer(transactionCreateRequest);
    }
    @GetMapping
    Page<TransactionResponse> findList(@RequestParam(required = false , defaultValue ="0") int page,
                                      @RequestParam(required = false,defaultValue = "25" )int size,
                                      @RequestParam(required = false,defaultValue = "DESC") String sortDirection){
        return transactionService.findList(page, size, sortDirection);

    }

}
