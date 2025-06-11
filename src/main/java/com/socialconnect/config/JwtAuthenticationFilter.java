package com.socialconnect.config;

import com.socialconnect.util.JwtUtil;
import com.socialconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JwtAuthenticationFilter.java
// JWT 认证过滤器的实现，主要功能：
// 从请求头中提取 JWT token
// 验证 token 的有效性
// 解析 token 获取用户信息
// 将用户信息设置到 SecurityContext 中
// 处理认证异常

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // **所有无需Token验证的公共路径**
        // 明确允许访问认证相关的API和静态资源（HTML, CSS, JS文件）
        if (requestURI.startsWith("/api/user/login") ||
            requestURI.startsWith("/api/user/register") ||
            requestURI.startsWith("/api/user/check-phone") ||
            requestURI.startsWith("/uploads/") || // 允许访问上传目录
            requestURI.equals("/login.html") ||   // 明确允许访问登录页面
            requestURI.equals("/test.html") ||    // 明确允许访问测试页面
            requestURI.equals("/") ||             // 允许访问根路径
            requestURI.endsWith(".html") ||       // 允许所有HTML文件
            requestURI.endsWith(".css") ||        // 允许所有CSS文件
            requestURI.endsWith(".js")) {         // 允许所有JS文件
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (JwtUtil.validateToken(token)) {
                    Long userId = JwtUtil.getUserIdFromToken(token);
                    // 设置认证信息
                    UserDetails userDetails = userService.loadUserByUsername(String.valueOf(userId));
                    
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute("userId", userId); // 设置用户ID到请求属性中

                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\": 401, \"msg\": \"Token已过期或无效\"}");
                    return;
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\": 401, \"msg\": \"认证错误: " + e.getMessage() + "\"}");
                return;
            }
        } else {
            // 如果没有Token或者Token格式不正确，并且不是公共路径，则返回未授权错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\": 401, \"msg\": \"请先登录\"}");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}
