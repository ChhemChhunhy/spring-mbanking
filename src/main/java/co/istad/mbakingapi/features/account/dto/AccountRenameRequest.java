package co.istad.mbakingapi.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRenameRequest(
        @NotBlank(message = "New Name is required")
        @Size(message = "Account Name must be less than 100 characters")
        String newName
) {
}
