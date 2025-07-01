package com.codelogium.ticketing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.codelogium.ticketing.security.filter.AuthenticationFilter;
import com.codelogium.ticketing.security.filter.ExceptionHandlerFilter;
import com.codelogium.ticketing.security.filter.JWTAuthorizationFilter;
import com.codelogium.ticketing.security.handler.CustomAccessDeniedHandler;
import com.codelogium.ticketing.security.handler.CustomAuthenticationEntryPoint;
import com.codelogium.ticketing.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/users/authenticate"); // Fix endpoint here if yours is /users/authenticate

        http
            .headers(h -> h.frameOptions(f -> f.disable())) // Allow H2 console frames
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/", "/users", "/users/authenticate").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/h2-console/**").permitAll()
                // Secured endpoints
                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/tickets/{ticketId}/status").hasAuthority("IT_SUPPORT")
                .requestMatchers("/users/{userId}/tickets/{ticketId}/info").hasAuthority("EMPLOYEE")
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> {
                e.accessDeniedHandler(new CustomAccessDeniedHandler());
                e.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
            })
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter)
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
