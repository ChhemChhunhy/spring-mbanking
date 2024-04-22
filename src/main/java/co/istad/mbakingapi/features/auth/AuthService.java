package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}
