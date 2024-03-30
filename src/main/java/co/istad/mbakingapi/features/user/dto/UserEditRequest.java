package co.istad.mbakingapi.features.user.dto;

import java.math.BigDecimal;

public record UserEditRequest(
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String village,
        String street,
        String employeeType,
        String position,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange
) {
}
