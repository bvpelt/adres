package com.bsoft.adres.config;

import com.bsoft.adres.jwt.AuthEntryPointJwt;
import com.bsoft.adres.jwt.AuthTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter jwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:81", "http://localhost:8080", "https://editor.swagger.io/", "https://editor-next.swagger.io/"));
            config.setAllowedMethods(List.of("*")); // Allow all HTTP methods
            config.setAllowedHeaders(List.of("*")); // Allow all headers
            return config;
        }));

        http.securityMatcher("/**");
        http.authorizeHttpRequests((requests) -> {
            requests
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/adres/api/v1/login/**").permitAll()
                    .requestMatchers("/favicon.ico").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_WRITE", "APP_MAINTENANCE")
                    .requestMatchers(HttpMethod.POST, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_WRITE", "APP_MAINTENANCE")
                    .requestMatchers(HttpMethod.PATCH, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("ALL", "APP_WRITE", "APP_MAINTENANCE")
                    .requestMatchers("/adres/api/v1/user/**", "/adres/api/v1/roles/**").hasAnyAuthority("ALL", "APP_MAINTENANCE")
                    .anyRequest().authenticated();
        });

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // create new session for each request

        // add exception handler
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(unauthorizedHandler);
        });

        // enable basic authentication
        http.httpBasic(Customizer.withDefaults());

        http.headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
