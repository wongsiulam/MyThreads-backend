package com.socialconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// Spring Security 的主要配置类，负责：
// 配置安全规则，定义哪些 URL 需要认证，哪些可以直接访问
// 配置密码加密器
// 配置认证管理器
// 配置 JWT 过滤器
// 配置跨域请求处理

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(
                    "/api/user/login", 
                    "/api/user/register", 
                    "/api/user/check-phone",
                    "/uploads/**",              // 允许访问上传的文件
                    "/login.html",              // 允许访问登录页面
                    "/test.html",               // 允许访问测试页面
                    "/",                        // 允许访问根路径
                    "/**/*.html",               // 允许所有HTML文件
                    "/**/*.css",                // 允许所有CSS文件
                    "/**/*.js"                  // 允许所有JS文件
                ).permitAll()  // 验证放行的路径
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin().disable()
            .httpBasic().disable();
        return http.build();
    }
} 