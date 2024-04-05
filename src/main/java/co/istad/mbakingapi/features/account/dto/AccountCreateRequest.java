package co.istad.mbakingapi.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(
        @NotBlank(message = "Alias is required")
        String alias,
        @NotNull(message = "First balance is required (5$ up)")
        BigDecimal balance,
        @NotNull(message = "Account type alias is required")
        String accountTypeAlias,
        @NotNull(message = "User owner is required")
        String userUuid,
        String cardNumber //if user create account type card
) {
}
