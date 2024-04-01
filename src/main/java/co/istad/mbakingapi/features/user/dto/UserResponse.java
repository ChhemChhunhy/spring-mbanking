package co.istad.mbakingapi.features.user.dto;

import java.time.LocalDate;

//does not have associated
public record UserResponse(
       String uuid,
       String name,
       String profileImage,
       String gender,
       LocalDate dob
) {
}
