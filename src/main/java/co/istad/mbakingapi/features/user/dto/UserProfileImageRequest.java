package co.istad.mbakingapi.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserProfileImageRequest(

        String mediaName
) {
}
