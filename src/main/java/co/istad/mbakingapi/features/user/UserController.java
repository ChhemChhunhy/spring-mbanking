package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.base.BaseMessage;
import co.istad.mbakingapi.base.BasedResponse;
import co.istad.mbakingapi.features.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final  UserService userService;
    @GetMapping
    Page<UserResponse> findList(@RequestParam (required = false,defaultValue = "0") int page,
                                @RequestParam(required = false,defaultValue = "2") int limit ){
        return userService.findList(page,limit);
    }

//    @GetMapping
//    List<UserResponse> findList(){
//        return userService.findList();
//    }

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
    UserDetailResponse findUserByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }

//    @GetMapping("/{uuid}")
//    UserResponse findUserByUuid(@PathVariable String uuid){
//        return userService.findUserByUuid(uuid);
//    }
    @PutMapping("/{uuid}/block")
    BaseMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }
    //Delete user by uuid (hard delete) /{uuid}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteByUuid(@PathVariable String uuid){
        userService.deleteByUuid(uuid);
    }

    //disable user by uuid( soft delete) /{uuid}/disable (true)
//    @PutMapping("/{uuid}/disable")
//    void disableByUuid(@PathVariable String uuid){
//        userService.disableByUuid(uuid);
//    }
    @PutMapping("/{uuid}/disable")
    BaseMessage disableUserByUuid(@PathVariable String uuid){
        return userService.disableUserByUuid(uuid);
    }

    //enable user by uuid( soft delete) /{uuid}/enable
//    @PutMapping("/{uuid}/enable")
//    void enableByUuid(@PathVariable String uuid){
//        userService.enableByUuid(uuid);
//    }
    @PutMapping("/{uuid}/enable")
    BaseMessage enableUserByUuid(@PathVariable String uuid){
        return userService.enableUserByUuid(uuid);
    }

    //update
    @PutMapping("/{uuid}/profile-image")
    BasedResponse<?> updateProfileImage(@PathVariable String uuid,
                                        @Valid @RequestBody UserProfileImageRequest userProfileImageRequest
                                   ){
        String newProfileImage = userService.updateProfileImage(uuid,userProfileImageRequest.mediaName());
        return BasedResponse.builder()
                .payload(newProfileImage)
                .build();
    }
}
