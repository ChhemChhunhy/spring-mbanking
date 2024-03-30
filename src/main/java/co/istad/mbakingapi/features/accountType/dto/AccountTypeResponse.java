package co.istad.mbakingapi.features.accountType.dto;

import co.istad.mbakingapi.domain.Account;

import java.util.List;

public record AccountTypeResponse(
        String name,
        String alias,
        String description
        //List<Account> accountList
) {
}
