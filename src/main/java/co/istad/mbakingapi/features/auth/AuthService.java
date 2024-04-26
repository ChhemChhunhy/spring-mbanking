package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.ChangePasswordRequest;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;
import co.istad.mbakingapi.features.auth.dto.RefreshTokenRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthService {
    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);
    AuthResponse login(LoginRequest loginRequest);
    void chnagePasswords(Jwt jwt,ChangePasswordRequest changePasswordRequest);
}
