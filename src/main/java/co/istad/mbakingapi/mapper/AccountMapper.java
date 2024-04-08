package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.Account;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.domain.UserAccount;
import co.istad.mbakingapi.features.account.dto.AccountCreateRequest;
import co.istad.mbakingapi.features.account.dto.AccountResponse;
import co.istad.mbakingapi.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);
    //custom
    @Mapping(source = "userAccountList",target = "user",qualifiedByName ="mapUserResponse" )
    AccountResponse toAccountResponse(Account account);

    //create default abstract
    //map user response

    //target =dto
    //source =entity

    // SourceType = UserCreateRequest (Parameter)
    // TargetType = User (ReturnType)


    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccountList){
        return toUserResponse(userAccountList.get(0).getUser());
    }
    UserResponse toUserResponse(User user);

    //@Mapping(source = "userAccountList",target = "user",qualifiedByName ="mapUserResponse" )
    List<AccountResponse> toAccountResponseList(List<Account> account);
}
