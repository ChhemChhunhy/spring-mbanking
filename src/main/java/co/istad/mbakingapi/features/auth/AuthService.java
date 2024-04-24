package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;
import co.istad.mbakingapi.features.auth.dto.RefreshTokenRequest;

public interface AuthService {
    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);
    AuthResponse login(LoginRequest loginRequest);
}
