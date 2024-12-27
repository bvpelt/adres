package com.bsoft.adres.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
public class JwtService {

    @Value("${application.key}")
    private String SECRET_KEY;

    @Value("${application.key-lifetime}")
    private long tokenValidityInMinutes;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 1000 * tokenValidityInMinutes * 60);

        String token3 = Jwts
                .builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claims(extraClaims)
                .issuer("Adres application")
                .subject(userDetails.getUsername())
                .issuedAt(startDate)
                .expiration(endDate)
                .signWith(getSigninKey()) // Use Keys.hmacSha256(keyBytes)
                .compact();

        log.debug("generateToken token: {}", token3);

        return token3;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {

        Claims claims = null;
        Claims claims1 = null;


        claims = Jwts
                .parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        claims1 = Jwts
                .parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.debug("extractAllClaims claim: {}", claims.toString());
        log.debug("extractAllClaims claim1: {}", claims1.toString());
        log.debug("extractAllClaims claim is {} equal to claim1: {}", claims1.toString().equals(claims.toString()), claims.toString().equals(claims1.toString()) ? "" : "not ");

        return claims;
    }

    /*
     Generate a key for jwt based on secret
     */
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

}
