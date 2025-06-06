package com.socialconnect.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.security.Key;


public class JwtUtil {
    
    private static final String SECRET = "a8f$2kL!9zQw3eR7tYp0sVx1cB4nM6jH"; // 长度要足够
    private static final long EXPIRATION = 24 * 60 * 60 * 1000; // 24小时

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 生成Token
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析Token，获取用户ID
    public static Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    // 校验Token是否有效
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace(); // 打印异常
            return false;
        }
    }
}
