package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.base.BaseMessage;
import co.istad.mbakingapi.features.user.dto.*;
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
    @PatchMapping("/{uuid}")
    UserResponse updateByUuid(@PathVariable String uuid,
                              @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateByUuid(uuid, userUpdateRequest);
    }


    @GetMapping("/{uuid}")
    UserResponse findUserByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }
    @PutMapping("/{uuid}/block")
    BaseMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuuid(uuid);
    }
    //Delete user by uuid (hard delete) /{uuid}


    //disable user by uuid( soft delete) /{uuid}/disable

    //enable user by uuid( soft delete) /{uuid}/enable
}
