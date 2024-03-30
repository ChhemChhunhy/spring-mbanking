package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import co.istad.mbakingapi.features.user.dto.UserDetailResponse;
import co.istad.mbakingapi.features.user.dto.UserEditRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //entity
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    void fromUserEditRequest(@MappingTarget User user, UserEditRequest userEditRequest);

   // void fromUserCreateRequest2(@MappingTarget UserCreateRequest userCreateRequest,User user);

    //
    // UserDetailResponse toUserDetailResponse(User user);
}
