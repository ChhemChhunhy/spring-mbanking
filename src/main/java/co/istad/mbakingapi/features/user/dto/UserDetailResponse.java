package co.istad.mbakingapi.features.user.dto;

import co.istad.mbakingapi.domain.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//have associated
public record UserDetailResponse(
        String uuid,
        String name,
        String profileImage,
        String gender,
        LocalDate dob,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String village,
        String street,
        String employeeType,
        String position,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        String studentIdCard,
        List<RoleNameResponse> roles
) {
}
