package com.magasin.demo.Security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))

                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/articles/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/articles/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/articles/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/articles/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.GET, "/authors/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/authors/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/authors/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/authors/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/authors/**").hasAnyAuthority("ADMIN", "USER")

                .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new JWTAuthorizationFilter(), BasicAuthenticationFilter.class);

        return http.build();

    }

}