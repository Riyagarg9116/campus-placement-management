

package com.placement.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized"
                    )
                )
                .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Access Denied"
                    )
                )
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Allow error dispatches
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                // Public authentication endpoints
                .requestMatchers("/api/auth/**").permitAll()

                // Company management: ADMIN only
                .requestMatchers(HttpMethod.POST, "/api/companies/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/companies/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/companies/**")
                    .hasRole("ADMIN")

                // View companies
                .requestMatchers(HttpMethod.GET, "/api/companies/**")
                    .hasAnyRole("ADMIN", "STUDENT")

                // Placement drive management: ADMIN only
                .requestMatchers(HttpMethod.POST, "/api/drives/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/drives/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/drives/**")
                    .hasRole("ADMIN")

                // View placement drives
                .requestMatchers(HttpMethod.GET, "/api/drives/**")
                    .hasAnyRole("ADMIN", "STUDENT")

                // All other endpoints require authentication.
                // Controller-level @PreAuthorize handles their roles.
                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
