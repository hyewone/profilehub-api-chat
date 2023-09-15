package com.goorm.profileboxapichat;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan("com.goorm")
public class ProfileBoxApiChatApplication {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public OpenAPI openAPI() {

        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("ProfileHub Chat API")
                        .version("1.0.0")
                        .description("ProfileHub API")
                        .contact(new Contact().name("hyewone").email("hyeneeOh@gmail.com")));
    }

    public static void main(String[] args) {
//        try (AnnotationConfigApplicationContext context
//                     = new AnnotationConfigApplicationContext(ProfileBoxApiChatApplication.class)) {
//            context.getBean(NettyContext.class).onClose().block();
//        }
//        SpringApplication.run(ProfileBoxApiChatApplication.class, args);

        SpringApplication application = new SpringApplication(ProfileBoxApiChatApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

}
