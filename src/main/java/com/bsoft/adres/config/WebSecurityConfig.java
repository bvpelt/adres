package com.bsoft.adres.config;


import com.bsoft.adres.security.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@Profile("runtime")
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
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080", "http://localhost:8081"));
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
                                .requestMatchers("/v3/api-docs/**", "swagger-ui.html","/swagger-ui/**", "/error").permitAll()
                                .requestMatchers("/adres/api/v1/login/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                                .requestMatchers(HttpMethod.POST, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                                .requestMatchers(HttpMethod.PATCH, "/adres/api/v1/adresses/**", "/adres/api/v1/persons/**").hasAnyAuthority("READ", "WRITE")
                                .requestMatchers("/adres/api/v1/user/**", "/adres/api/v1/roles/**").hasAuthority("WRITE")
//                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
        ;

        return http.build();
    }

    @Bean
    @Order(2) // Lower order to apply after the main security chain
    public SecurityFilterChain actuatorFilterChain(HttpSecurity http) throws Exception {
        http

                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080", "http://localhost:8081"));
                    config.setAllowedMethods(List.of("*")); // Allow all HTTP methods
                    config.setAllowedHeaders(List.of("*")); // Allow all headers
                    // Allow credentials for actuator endpoint
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
//                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
//               .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(AbstractHttpConfigurer::disable)


                .securityMatcher("/actuator/**")
                .authorizeHttpRequests(registry -> registry
//                        .requestMatchers("/actuator").hasAuthority("ACTUATOR_ROLE")
//                        .requestMatchers(new RequestHeaderRequestMatcher("Authorization")).hasAnyAuthority("ACTUATOR_ROLE")
                        .anyRequest().hasAuthority("ACTUATOR_ROLE") // Adjust the role as needed
                )
                .httpBasic(Customizer.withDefaults()) // Use basic authentication for actuator
        ;
        return http.build();
    }

}
