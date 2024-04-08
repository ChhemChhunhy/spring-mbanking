package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.domain.UserAccount;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.user.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //entity
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    void fromUserEditRequest(@MappingTarget User user, UserEditRequest userEditRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest,@MappingTarget User user);

    UserDetailResponse toUserDetailResponse(User user);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> user);
    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccountList){
        return toUserResponse(userAccountList.get(0).getUser());
    }

    // void fromUserCreateRequest2(@MappingTarget UserCreateRequest userCreateRequest,User user);

    //
    // UserDetailResponse toUserDetailResponse(User user);
}
