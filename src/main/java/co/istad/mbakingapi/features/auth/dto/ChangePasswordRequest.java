package co.istad.mbakingapi.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "Old Password is required")
        String oldPassword,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Password is required")
        String confirmPassword
) {
}
