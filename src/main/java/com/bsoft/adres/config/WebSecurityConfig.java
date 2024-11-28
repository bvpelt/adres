package com.bsoft.adres.config;


import com.bsoft.adres.security.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.debug("Starting bCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200"));
                    config.setAllowedMethods(List.of("*")); // Allow all HTTP methods
                    config.setAllowedHeaders(List.of("*")); // Allow all headers
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/login/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                        .requestMatchers(HttpMethod.POST, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                        .requestMatchers(HttpMethod.PATCH, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                        .requestMatchers("/actuator/**", "/adres/api/v1/user/**", "/adres/api/v1/roles/**").hasAuthority("WRITE")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
        ;

        return http.build();
    }

}
