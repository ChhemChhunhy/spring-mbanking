package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.features.user.dto.UserChangePasswordRequest;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import co.istad.mbakingapi.features.user.dto.UserEditRequest;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
    void userChangePassword(UserChangePasswordRequest userChangePasswordRequest);
    void editUserByUuid(String uuid,UserEditRequest userEditRequest);

}
