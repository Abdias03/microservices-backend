package com.marketplace.users.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void generateAndValidateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token), "Token should be valid");
    }

    @Test
    void extractUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "anotherUser";
        String token = jwtUtil.generateToken(username);
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }
}
