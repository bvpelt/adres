package com.bsoft.adres.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class JwtFilterChain {


    //private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //private final AuthenticationProvider authenticationProvider;

/*
    @Bean
    // @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/error").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // create new session for each request
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        ;
        return http.build();
    }

 */
}
