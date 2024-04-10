package co.istad.mbakingapi.features.transaction;

import co.istad.mbakingapi.domain.Account;
import co.istad.mbakingapi.domain.Transaction;
import co.istad.mbakingapi.features.account.AccountRepository;
import co.istad.mbakingapi.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbakingapi.features.transaction.dto.TransactionResponse;
import co.istad.mbakingapi.mapper.TransactionMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final TransactionMapper transactionMapper;
    @Transactional
    @Override
    public TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest) {
        log.info("transfer(TransactionCreateRequest transactionCreateRequest) : {}",transactionCreateRequest);

        Account owner = accountRepository.findByActNo(transactionCreateRequest.ownerActNo()).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account owner has not been found"
                )
        );
        Account transferReceiver= accountRepository.findByActNo(transactionCreateRequest.transferReceiverActNo()).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account transfer has not been found"
                )
        );

        //check amount transfer (balance < amount)
        if (owner.getBalance().doubleValue()<transactionCreateRequest.amount().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance"
            );
        }

        //check account if account name the same cannot transfer
        if (owner.getAlias().equals(transferReceiver.getAlias())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot transfer to the same account"
            );
        }

        //check amount transfer limit
        if(transactionCreateRequest.amount().doubleValue()>=owner.getTransferLimit().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transfer Transaction has been over the transfer limit"
            );
        }


        // (-)
        owner.setBalance(owner.getBalance().subtract(transactionCreateRequest.amount()));
        //accountRepository.save(owner);
        // (+)
        transferReceiver.setBalance(transferReceiver.getBalance().add(transactionCreateRequest.amount()));

        //check amount transfer limit
        if(transactionCreateRequest.amount().doubleValue()>=owner.getTransferLimit().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transfer Transaction has been over the transfer limit"
            );
        }


        // (-)
        owner.setBalance(owner.getBalance().subtract(transactionCreateRequest.amount()));
        //accountRepository.save(owner);
        // (+)
        transferReceiver.setBalance(transferReceiver.getBalance().add(transactionCreateRequest.amount()));
       // accountRepository.save(transferReceiver);
        Transaction transaction = transactionMapper.fromTransactCreateRequest(transactionCreateRequest);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(transferReceiver);
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus(true);
        transaction.setTransactionAt(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> findList(int page, int size, String sort,String transactionType) {

        if (page<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid page number,Page greater than or equal to zero");
        }
        if (size<1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid page size,Page size greater than or equal to one");
        }
        Sort.Direction direction;
        String property;
        if (sort != null && sort.startsWith("date")) {
            String[] sortParts = sort.split(":");
            if (sortParts.length == 2 && sortParts[1].equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
            property = "transactionAt"; // Assuming transaction date property name
        } else {

            direction = Sort.Direction.DESC;
            property = "transactionAt";
        }
        Sort sortByTransactionDate = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, size, sortByTransactionDate);
        Page<Transaction> transactions;
        transactionType = transactionType.toUpperCase();
        if (transactionType.equals("TRANSFER")){
            transactions= transactionRepository.findByTransactionType(transactionType,pageRequest);
        }
        else if (transactionType.equals("PAYMENT")){
            transactions= transactionRepository.findByTransactionType(transactionType,pageRequest);
        }else {
            transactions= transactionRepository.findAll(pageRequest);
        }
        return transactions.map(transactionMapper::toTransactionResponse);
    }
}
