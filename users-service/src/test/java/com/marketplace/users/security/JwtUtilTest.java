package com.marketplace.users.security;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void generatedTokenIsValid() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser", List.of("ROLE_USER"));
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void getUsernameFromTokenReturnsExpectedUser() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "anotherUser";
        String token = jwtUtil.generateToken(username);
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }
}
