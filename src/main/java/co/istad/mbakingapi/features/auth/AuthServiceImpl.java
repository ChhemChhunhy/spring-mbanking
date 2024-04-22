package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;
import co.istad.mbakingapi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    //inject bean
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        //core
        //authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
       auth= daoAuthenticationProvider.authenticate(auth);

        CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUsername())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer(userDetails.getUsername())
                .build();
      String accessToken=  jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();



        return new AuthResponse(
                "Bearer",
                accessToken,
                ""
        );
    }
}
