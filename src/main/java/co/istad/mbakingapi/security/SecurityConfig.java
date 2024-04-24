package co.istad.mbakingapi.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(@Qualifier("refreshJwtDecoder") JwtDecoder refreshJwtDecoder){
        JwtAuthenticationProvider jwtAuthentication = new JwtAuthenticationProvider(refreshJwtDecoder);
        return jwtAuthentication;
    }

    //jdbc:security
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        //endCoder decryption
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //logic for security
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/**").permitAll()
                        //.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:update")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAuthority("SCOPE_user:write")
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAuthority("SCOPE_user:read")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAuthority("SCOPE_user:write")

                        .requestMatchers(HttpMethod.POST,"/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                        //.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:update")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                        .requestMatchers(HttpMethod.GET,"/api/v1/accounts/**").hasAnyAuthority("SCOPE_ROLE_ADMIN","SCOPE_ROLE_STAFF")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                        .anyRequest().authenticated());
        //security mechanism
        //httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.oauth2ResourceServer(jwt -> jwt.jwt(Customizer.withDefaults()));
        //disable token for post and put
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //stateless
        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
    //ssh-keygen : keypair (public key and private key)
    //access token
    @Primary
    @Bean
    KeyPair keyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //create Asymmetric key RSA Key endCode and Decode : generate jwt token
    @Primary
    @Bean
    RSAKey rsaKey (KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    //jwkSource for create endCoder
    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    //issuer generated token when user issuer
    @Primary
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
      }
    //jwt decoder when user submit header authorization
    @Primary
    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }


    @Bean("refreshRsaKey")
    RSAKey refreshRsaKey (KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    //jwkSource for create endCoder
    @Bean("refreshJwkSource")
    JWKSource<SecurityContext> refreshJwkSource(@Qualifier("refreshRsaKey") RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    //issuer generated token when user issuer
    @Bean("refreshJwtEncoder")
    JwtEncoder refreshJwtEncoder(@Qualifier("refreshJwkSource") JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }
    //jwt decoder when user submit header authorization
    @Bean("refreshJwtDecoder")
    JwtDecoder refreshJwtDecoder(@Qualifier("refreshRsaKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

}
