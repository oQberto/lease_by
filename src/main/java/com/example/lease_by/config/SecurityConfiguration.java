package com.example.lease_by.config;

import com.example.lease_by.service.impl.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] URL_WHITE_LIST = {
            "/login",
            "/accounts/forgot-password",
            "/accounts/register",
            "/accounts/user/**",
            "/rentals/**",
            "/cities",
            "/api/v1/**",
            "/css/**",
            "/image/**",
            "/js/**"
    };
    private final SecurityUserService securityUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(URL_WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/cities"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/cities")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/cities")
                        .deleteCookies("JSESSIONID"))
                .build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            UserDetails userDetails = securityUserService.loadUserByUsername(email);
            DefaultOidcUser defaultOidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> methods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(
                    SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> methods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(defaultOidcUser, args)
            );
        };
    }
}
