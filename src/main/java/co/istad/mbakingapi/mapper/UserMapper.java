package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.user.dto.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //entity
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    void fromUserEditRequest(@MappingTarget User user, UserEditRequest userEditRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest,@MappingTarget User user);

    UserResponse toUserResponse(User user);


    // void fromUserCreateRequest2(@MappingTarget UserCreateRequest userCreateRequest,User user);

    //
    // UserDetailResponse toUserDetailResponse(User user);
}
