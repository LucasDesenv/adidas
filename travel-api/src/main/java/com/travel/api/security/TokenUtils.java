package com.travel.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lusouza on 22/07/18.
 */
public final class TokenUtils {
    private static final String  TOKEN_SECRET = "4a1a71a9ebb86dad178c14efb6wed2";

    private TokenUtils() {}

    public static String createToken(Claims claims) {
        final Instant tokenExpirationTime = Instant.now().plus(24, ChronoUnit.HOURS);
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    public static Claims decodeToken(final String token) throws IllegalAccessException {
        if (token == null) {
            throw new IllegalAccessException("Invalid Authorization header.");
        }
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
    }
}
