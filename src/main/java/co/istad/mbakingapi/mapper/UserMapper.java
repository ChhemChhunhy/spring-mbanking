package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import co.istad.mbakingapi.features.user.dto.UserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //entity
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

   // void fromUserCreateRequest2(@MappingTarget UserCreateRequest userCreateRequest,User user);

    //
    // UserDetailResponse toUserDetailResponse(User user);
}
