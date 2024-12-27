package com.bsoft.adres.config;

import com.bsoft.adres.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080", "https://editor.swagger.io/", "https://editor-next.swagger.io/"));
                    config.setAllowedMethods(List.of("*")); // Allow all HTTP methods
                    config.setAllowedHeaders(List.of("*")); // Allow all headers
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/h2-console/**").hasAnyAuthority("ALL", "APP_SECURITY_ACCESS", "APP_FULL_ACCESS")
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/error").permitAll()
                                .requestMatchers("/adres/api/v1/login/**").permitAll()
                                .requestMatchers("/actuator/**").hasAnyAuthority("ALL", "APP_SECURITY_ACCESS")
                                .requestMatchers(HttpMethod.DELETE, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_FULL_ACCESS")
                                .requestMatchers(HttpMethod.POST, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_FULL_ACCESS")
                                .requestMatchers(HttpMethod.PATCH, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_FULL_ACCESS")
                                .requestMatchers("/adres/api/v1/user/**", "/adres/api/v1/roles/**").hasAnyAuthority("ALL", "APP_SECURITY_ACCESS")
                                .requestMatchers(HttpMethod.GET, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").permitAll()
                                .anyRequest().authenticated()
                )
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // create new session for each request
                .formLogin(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

}
