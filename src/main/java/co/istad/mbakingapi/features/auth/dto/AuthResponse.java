package co.istad.mbakingapi.features.auth.dto;

public record AuthResponse(
        String type,
        String accessToken,
        String refreshToken
) {
}
