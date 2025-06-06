package com.socialconnect.config;

import com.socialconnect.util.JwtUtil;
import com.socialconnect.entity.User;
import com.socialconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (JwtUtil.validateToken(token)) {
                Long userId = JwtUtil.getUserIdFromToken(token);
                // 你可以将userId存入request属性，后续Controller可用
                request.setAttribute("currentUserId", userId);
            } else {
                // Token无效，直接返回401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
