package co.istad.mbakingapi.features.token;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public class TokenServiceImpl implements TokenService {
    @Override
    public AuthResponse createToken(Authentication auth) {
        return null;
    }

    @Override
    public String createAccessToken(Authentication auth) {
        return null;
    }

    @Override
    public String createRefreshToken(Authentication auth) {
        return null;
    }
}
