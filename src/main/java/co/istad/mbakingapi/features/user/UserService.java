package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.features.user.dto.UserCreateRequest;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);

}
