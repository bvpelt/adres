package com.bsoft.adres.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Profile("runtime")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("12345"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("54321"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    @Order(0)
    public SecurityFilterChain actuatorFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/actuator/**") // Match all adresses endpoints
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/actuator/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/actuator/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/actuator/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/actuator/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/swagger_ui/**") // Match all adresses endpoints
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger_ui/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // TODO - Fix security basic authentication
    @Bean
    @Order(2)
    public SecurityFilterChain addressesFilterChain(HttpSecurity http) throws Exception {
        http
//                .securityMatcher("/adresses/**") // Match all adresses endpoints
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/adresses/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/adresses/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PATCH, "/adresses/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.DELETE, "/adresses/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    // TODO - Fix security basic authentication
    @Bean
    @Order(3)
    public SecurityFilterChain personsFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/persons/**") // Match all adresses endpoints
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/persons/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/persons/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PATCH, "/persons/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.DELETE, "/persons/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    @Bean
    @Order(4)
    public SecurityFilterChain loginFilterChainxx(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/login/**") // Match all adresses endpoints
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    UrlBasedCorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "DELETE", "PATCH", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
