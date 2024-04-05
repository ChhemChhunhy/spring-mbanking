package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.base.BaseMessage;
import co.istad.mbakingapi.domain.Role;
import co.istad.mbakingapi.domain.User;
import co.istad.mbakingapi.features.user.dto.*;
import co.istad.mbakingapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final  RoleRepository roleRepository;
    @Value("${media.base-uri}")
    private String mediaBaseUri;
    @Override
    public UserResponse findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User doses not exist"
                )
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public void createNew(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number has already been existed!"
            );
        }


        if (userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card ID has already been existed!"
            );
        }

        if (userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student card ID has already been existed!"
            );
        }


        if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Passwords do not match"
            );
        }

        //DTO pattern (mapstruct ft. lombok)
        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER").orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Role not found"
                )
        );
        //create dynamic roles (not map struct )
        userCreateRequest.roles().forEach(r-> {
            Role newRole = roleRepository.findByName(r.name()).orElseThrow(
                    () -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,"Role not found"
                    )
            );
            roles.add(newRole);
        });

        //mange transaction
        roles.add(userRole);
        //Role subscriber = new Role();
        //subscriber.setId(5)
        //subscriber.setName("SUBSCRIBER");
        //roles.add(subscriber);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void userChangePassword(UserChangePasswordRequest userChangePasswordRequest) {
        if (!userRepository.existsByName(userChangePasswordRequest.name())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cannot found user name"+userChangePasswordRequest.name()
            );
        }

        User user = userRepository.findByPassword(userChangePasswordRequest.oldPassword()).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Password doest not match"
                )
        );
        if(!userChangePasswordRequest.newPassword().equals(userChangePasswordRequest.confirmedNewPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match.Please try again."
            );
        }
        user.setPassword(userChangePasswordRequest.newPassword());
        userRepository.save(user);


    }

    @Override
    public void editUserByUuid(String uuid, UserEditRequest userEditRequest) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User doses not exist"
                )
        );
        userMapper.fromUserEditRequest(user,userEditRequest);
        userRepository.save(user);
    }

    @Override
    public UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest) {
        //exist returns boolean
        //patch
        //find return object
        //check uuid
        //User user = userRepository.findByUuid(uuid)
        //path
        User user = userRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User doses not exist"
                )
        );
        log.info("Before user :"+user.getName());
        userMapper.fromUserUpdateRequest(userUpdateRequest,user);
        log.info("After user :"+user.getName());
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
       // return null;
    }

    @Override
    public UserDetailResponse findUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User doses not exist"
                )
        );
        //override toString stackOverFlow because it use bidirectional
        //user.getRoles().forEach(role -> log.info("Roles: {}",role));
        return userMapper.toUserDetailResponse(user);
    }

    /*
    * Derived Query and JPQL = read
    * transaction = update , post
    * */
    @Transactional
    @Override
    public BaseMessage blockByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User doses not exist"
            );
        }
        userRepository.blockByUuid(uuid);
        return new BaseMessage("User has  been blocked");
    }
    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User does not exist"
            );
        }
        userRepository.deleteByUuid(uuid);
    }



    @Transactional
    @Override
    public void disableByUuid(String uuid) {
        if (userRepository.existsByUuidAndIsDeletedFalse(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User does not exist"
            );
        }
        userRepository.updateIsDeleted(uuid);
    }

    @Transactional
    @Override
    public void enableByUuid(String uuid) {
        if (userRepository.existsByUuidAndIsDeletedTrue(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User does not exist"
            );
        }
        userRepository.updateIsDeletedFalse(uuid);
    }

    @Transactional
    @Override
    public BaseMessage enableUserByUuid(String uuid) {
        if (userRepository.existsByUuidAndIsDeletedTrue(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User does not exist"
            );
        }
        userRepository.updateIsDeletedFalse(uuid);
        return new BaseMessage("User is already enabled");
    }

    @Transactional
    @Override
    public BaseMessage disableUserByUuid(String uuid) {
        if (userRepository.existsByUuidAndIsDeletedFalse(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User does not exist"
            );
        }
        userRepository.updateIsDeleted(uuid);
        return new BaseMessage("User is disabled");
    }

    @Override
    public Page<UserResponse> findList(int page,int limit) {
        PageRequest pageRequest = PageRequest.of(page,limit);
        Page<User> users = userRepository.findAll(pageRequest);
        //List<User> user = userRepository.findAll();
        //return userMapper.toUserResponseList(user);
        return users.map(userMapper::toUserResponse);
    }

    @Override
    public String updateProfileImage(String uuid,String mediaName) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found!"
                )
        );
        user.setProfileImage(mediaName);
        userRepository.save(user);
        return mediaBaseUri+"IMAGE/"+mediaName;
    }
}
