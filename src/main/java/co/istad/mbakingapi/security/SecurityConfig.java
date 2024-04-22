package co.istad.mbakingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

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
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/accounts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/accounts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/v1/accounts/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"api/v1/accounts/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE,"api/v1/accounts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/account-types/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"api/v1/card-types/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"api/v1/transactions/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"api/v1/transactions/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"api/v1/transactions/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET,"api/v1/accounts/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET,"api/v1/payments/**").hasRole("STAFF")
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
}
