package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.features.user.dto.UserChangePasswordRequest;
import co.istad.mbakingapi.features.user.dto.UserCreateRequest;
import co.istad.mbakingapi.features.user.dto.UserEditRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final  UserService userService;
    @PostMapping
    void createNew(@Valid @RequestBody UserCreateRequest userCreateRequest){
        userService.createNew(userCreateRequest);
    }

    @PutMapping("/changingPasswords")
    void userChangePassword(@Valid @RequestBody  UserChangePasswordRequest userChangePasswordRequest){
        userService.userChangePassword(userChangePasswordRequest);
    }

    @PutMapping("/uuid/{uuid}")
    void editUserByUuid(@PathVariable String uuid,@RequestBody UserEditRequest userEditRequest){
       userService.editUserByUuid(uuid,userEditRequest);
    }
}
