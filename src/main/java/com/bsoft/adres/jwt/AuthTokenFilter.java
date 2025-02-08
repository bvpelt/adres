package com.bsoft.adres.jwt;

import com.bsoft.adres.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.trace("doFilterInternal called for method: {} URI: {}", request.getMethod(), request.getRequestURI());
        log.trace("doFilterInternal called with respone status: {}", response.getStatus());
        log.trace("doFilterInternal called with filter: {}", filterChain.toString());

        try {
            checkAPIKey(request);

            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                log.debug("Roles from JWT: {}", userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Authenticating the user for the duration of the request
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.toString());
        }
        
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        log.trace("parseJwt called for URI: {}, token: {}", request.getRequestURI(), jwt);

        return jwt;
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

    private String getClientIpAddr(HttpServletRequest request) {
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
