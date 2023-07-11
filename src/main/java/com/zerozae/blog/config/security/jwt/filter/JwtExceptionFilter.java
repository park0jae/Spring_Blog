package com.zerozae.blog.config.security.jwt.filter;

import com.zerozae.blog.exception.ValidateTokenException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{

        }catch (ValidateTokenException e){
            response.sendRedirect("exception/invalid-token");
            return ;
        }
    }
}
