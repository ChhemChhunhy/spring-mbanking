package co.istad.mbakingapi.features.auth;

import co.istad.mbakingapi.features.auth.dto.AuthResponse;
import co.istad.mbakingapi.features.auth.dto.LoginRequest;
import co.istad.mbakingapi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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
    private final JwtEncoder jwtEncoder;
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        //core
        //authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
       auth= daoAuthenticationProvider.authenticate(auth);

        CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
//        String scope = userDetails.getAuthorities().stream()
//                .filter(grantedAuthority -> )
//                .map(grantedAuthority -> grantedAuthority.getAuthority())
//                .collect(Collectors.joining());
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                //.filter(authority -> !authority.startsWith("ROLe_") )
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();
        //scope
//        String scope = auth.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUsername())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer(userDetails.getUsername())
                //claim scope
                .claim("scope",scope)
                .build();
      String accessToken=  jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return new AuthResponse(
                "Bearer",
                accessToken,
                ""
        );
    }
}
