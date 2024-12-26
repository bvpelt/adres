package com.bsoft.adres.auth;

import com.bsoft.adres.security.MyUserDetailsService;
import com.bsoft.adres.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor // Generates constructor for each final member field
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final MyUserDetailsService userDetailsService;

    private final ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.trace("doFilterInternal - check request requirements");

        checkAPIKey(request);

        checkJWTToken(request, response, filterChain);
    }

    private void checkJWTToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final String authHeader = request.getHeader("Authorization"); // bearer token
        final String jwt;
        final String username;

        if ((authHeader == null) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) { // user not connected
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void checkAPIKey(HttpServletRequest request) throws ServletException {
        final String requestUri = request.getRequestURI();
        final String xapiHeader = request.getHeader("X-API-KEY");

        if (requestUri != null) {
            log.trace("requestUri: {}", requestUri);
            if (requestUri.startsWith("/adres/api/v1")) {
                log.trace("requestUri match /adres/api/v1: {}", requestUri);
                String refererHeader = request.getHeader("Referer");
                String ipAdres = getClientIpAddr(request);
                if (xapiHeader == null) {
                    log.error("doFilterInternal - No X-API-KEY, referer: {}, ipadres: {}", (refererHeader != null ? refererHeader : ""), ipAdres);
                    throw new ServletException("X-API-KEY required");
                } else {
                    if (!apiKeyService.isValidApiKey(xapiHeader)) {
                        log.error("doFilterInternal - No X-API-KEY, referer: {}, ipadres: {}", (refererHeader != null ? refererHeader : ""), ipAdres);
                        throw new ServletException("X-API-KEY has invalid format or is not known!");
                    }
                }
            }
        }
    }

    public String getClientIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
