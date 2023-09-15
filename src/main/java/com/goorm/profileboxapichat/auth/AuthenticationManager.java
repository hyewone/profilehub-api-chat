package com.goorm.profileboxapichat.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goorm.profileboxapichat.entity.Member;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {


    private final RestTemplate restTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof BearerToken) // 인스턴스 검사
                .cast(BearerToken.class)
                .flatMap(jwt -> validate(jwt));
    }

    @SneakyThrows
    private Mono<Authentication> validate(BearerToken token){
        Member member = null;
        String jwtToken = token.getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, JwtProperties.TOKEN_PREFIX + jwtToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> authResponse = restTemplate.exchange(
                "http://localhost:7002" + "/v1/auth/verify",
                HttpMethod.GET,
                entity,
                String.class
        );

        // auth 서버 응답을 처리
        if (authResponse.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            member = objectMapper.readValue(authResponse.getBody(), Member.class);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(member.getMemberType().toString()));

//            UserDetails userDetails = User.withUsername(member.getMemberEmail())
//                    .password("")
//                    .roles(member.getMemberType().toString())
//                    .build();
            return Mono.just(new UsernamePasswordAuthenticationToken(member, null, authorities));
        }
        return Mono.empty();
    }
}