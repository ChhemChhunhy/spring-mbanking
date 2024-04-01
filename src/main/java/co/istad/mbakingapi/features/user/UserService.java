package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.base.BaseMessage;
import co.istad.mbakingapi.features.user.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
    void userChangePassword(UserChangePasswordRequest userChangePasswordRequest);
    void editUserByUuid(String uuid,UserEditRequest userEditRequest);
    //have many responses
    //asociate review detailsResponse and response
    UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest);

    UserDetailResponse findUserByUuid(String uuid);
    UserResponse findByUuid(String uuid);
    BaseMessage blockByUuid(String uuid);
    void deleteByUuid(String uuid);

    void disableByUuid(String uuid);
    void enableByUuid(String uuid);

    BaseMessage enableUserByUuid(String uuid);
    BaseMessage disableUserByUuid(String uuid);

    Page<UserResponse> findList(int pages, int limit);
}
