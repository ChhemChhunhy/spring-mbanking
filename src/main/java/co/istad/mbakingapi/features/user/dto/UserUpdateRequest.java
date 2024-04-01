package co.istad.mbakingapi.features.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

//partaily update (patch) no need to validate
public record UserUpdateRequest(

        String name,
        String gender,
        LocalDate dob,
        String studentIdCard
) {
}
