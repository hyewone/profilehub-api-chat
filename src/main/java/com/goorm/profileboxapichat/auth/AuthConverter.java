package com.goorm.profileboxapichat.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(
                exchange.getRequest().getQueryParams().getFirst("token")
                )
//                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
//                )
//                .filter(authHeader -> authHeader.startsWith("Bearer "))
//                .map(authHeader -> authHeader.substring(7))
                .map(authHeader -> new BearerToken(authHeader));
    }
}
