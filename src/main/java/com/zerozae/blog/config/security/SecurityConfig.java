package com.zerozae.blog.config.security;

import com.zerozae.blog.config.security.detailsservice.MemberDetailsService;
import com.zerozae.blog.config.security.handler.JwtAccessDeniedHandler;
import com.zerozae.blog.config.security.handler.JwtAuthenticationEntryPoint;
import com.zerozae.blog.config.security.jwt.filter.JwtExceptionFilter;
import com.zerozae.blog.config.security.jwt.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final MemberDetailsService memberDetailsService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 시큐리티 예외 핸들러
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwtFilter 등록
                .addFilterBefore(jwtExceptionFilter, JwtFilter.class)
                .userDetailsService(memberDetailsService); // 시큐리티 UserDetailsService

        return http.build();
    }
}
