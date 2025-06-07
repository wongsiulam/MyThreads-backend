package com.socialconnect.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.security.Key;
import java.util.Base64; // 导入Base64工具类

public class JwtUtil {
    
    private static final String SECRET = "c95b35ca442e3e024eb26ea93691d852eeab43c239507517f0a29a8dc0b4861d2c6712ca1d2b6e0d18d22b64e01a6eda"; // 长度要足够
    private static final long EXPIRATION = 24 * 60 * 60 * 1000; // 24小时

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 静态代码块，在类加载时打印SECRET和KEY
    static {
        System.out.println("JwtUtil 初始化 SECRET: " + SECRET);
        System.out.println("JwtUtil 初始化 KEY (Base64): " + Base64.getEncoder().encodeToString(KEY.getEncoded()));
    }

    // 生成Token
    public static String generateToken(Long userId) {
        System.out.println("生成Token时使用的 SECRET: " + SECRET);
        System.out.println("生成Token时使用的 KEY (Base64): " + Base64.getEncoder().encodeToString(KEY.getEncoded()));

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析Token，获取用户ID
    public static Long getUserIdFromToken(String token) {
        // 解析Token时，如果 SECRET 不一致，这里会抛异常，所以这部分日志可能看不到
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
            System.out.println("校验Token时收到的 SECRET: " + SECRET);
            System.out.println("校验Token时收到的 KEY (Base64): " + Base64.getEncoder().encodeToString(KEY.getEncoded()));
            System.out.println("正在校验的Token: " + token); // 再次打印确认传入的token

            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token校验失败！异常信息：" + e.getMessage());
            e.printStackTrace(); // 打印完整堆栈
            return false;
        }
    }
}
