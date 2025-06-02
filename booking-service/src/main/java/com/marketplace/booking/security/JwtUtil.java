package com.marketplace.booking.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final long EXPIRATION_TIME = 86_400_000; // 24h
    private static final String SECRET_KEY = "clave-super-secreta-y-larga-para-jwt-2025";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }
    

    public List<String> getRolesFromToken(String token) {
    	Claims claims = Jwts.parserBuilder()
    	        .setSigningKey(getSigningKey())
    	        .build()
    	        .parseClaimsJws(token)
    	        .getBody();

    	    // rolesRaw es List<?>; luego mapeamos cada elemento a su toString()
    	    List<?> rolesRaw = claims.get("roles", List.class);
    	    return rolesRaw.stream()
    	                   .map(Object::toString)
    	                   .collect(Collectors.toList());
    }

}
