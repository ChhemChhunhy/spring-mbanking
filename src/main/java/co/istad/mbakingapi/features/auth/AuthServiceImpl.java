package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;
import co.istad.mbakingapi.features.auth.dto.RefreshTokenRequest;
import co.istad.mbakingapi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    //inject bean
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtEncoder;
    private JwtEncoder refreshJwtEncoder;
    @Qualifier("refreshJwtEncoder")
    @Autowired
    public void setRefreshJwtEncoder(JwtEncoder refreshJwtEncoder) {
        this.refreshJwtEncoder = refreshJwtEncoder;
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        auth= jwtAuthenticationProvider.authenticate(auth);
        Jwt jwt =(Jwt)auth.getPrincipal();
        log.info("user name: {}",jwt.getId());
        log.info("scope: {}",jwt.getClaimAsString("scope"));

        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer(jwt.getId())
                .claim("scope",jwt.getClaimAsString("scope"))
                .build();

        String accessToken=  jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        //refresh token
       // String refreshToken = refreshJwtEncoder.encode(JwtEncoderParameters.from(refreshJwtClaimsSet)) .getTokenValue();
        return new AuthResponse(
                "Bearer",
                accessToken,
                refreshTokenRequest.refreshToken()
        );
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        //core
        //authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
       auth= daoAuthenticationProvider.authenticate(auth);

        CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());

        String scope = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                //.filter(authority -> !authority.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

        log.info("Scope {} :",scope);

        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUsername())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer(userDetails.getUsername())
                .claim("scope",scope)
                .build();

        JwtClaimsSet refreshJwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUsername())
                .subject("Refresh Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .issuer(userDetails.getUsername())
                .claim("scope",scope)
                .build();

      String accessToken=  jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
      //refresh token
      String refreshToken = refreshJwtEncoder.encode(JwtEncoderParameters.from(refreshJwtClaimsSet)) .getTokenValue();
        return new AuthResponse(
                "Bearer",
                accessToken,
                refreshToken
        );
    }
}
