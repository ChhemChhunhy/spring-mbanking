package co.istad.mbakingapi.features.account.dto;

import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.user.dto.UserDetailResponse;
import co.istad.mbakingapi.features.user.dto.UserResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String alias,
        String actName,
        BigDecimal balance,
        BigDecimal transferLimit,
        AccountTypeResponse accountTypeResponse,
        UserResponse userResponse
) {
}
