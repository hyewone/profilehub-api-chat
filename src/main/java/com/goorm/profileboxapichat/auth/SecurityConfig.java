package com.goorm.profileboxapichat.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

//@EnableWebSecurity
@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthConverter authConverter;

//    private final CorsFilter corsFilter;
//    private final AuthenticationConfiguration authenticationConfiguration;
//    private final JwtProvider jwtProvider;
//    private final RestTemplate restTemplate;
    private final String[] allowedUrls = {"/"
                            , "/v1/open/**"
                            , "/webjars/swagger-ui/**"
                            , "/swagger-resources/**"
                            , "/v3/api-docs/**"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter filter =  new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(authConverter);

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterAfter(filter, SecurityWebFiltersOrder.AUTHENTICATION)
//                .addFilterBefore(corsFilter)
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(allowedUrls).permitAll()
                .anyExchange().authenticated();
        return http.build();
    }
}
