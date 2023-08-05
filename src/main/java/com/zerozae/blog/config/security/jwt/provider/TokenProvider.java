package com.zerozae.blog.config.security.jwt.provider;


import com.zerozae.blog.config.security.details.MemberDetails;
import com.zerozae.blog.exception.ValidateTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTH_KEY = "AUTHORITY";
    private static final String AUTH_ID = "ID";
    private static final String AUTH_USERNAME = "USERNAME";
    private final long tokenValidityMilliSeconds;
    private final String originSecretKey;

    private Key secretKey;

    public TokenProvider(@Value("${jwt.token-validity-in-seconds}") long tokenValiditySeconds,
                      @Value("${jwt.secret_key}") String originSecretKey) {
        this.tokenValidityMilliSeconds =  tokenValiditySeconds * 1000;
        this.originSecretKey = originSecretKey;
    }

    @PostConstruct
    public void initKey() {
        byte[] keyBytes = Decoders.BASE64.decode(originSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {

        MemberDetails principal = (MemberDetails) authentication.getPrincipal();

        String authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date validity = new Date(now + this.tokenValidityMilliSeconds);

        String jwt = Jwts.builder()
                .addClaims(Map.of(AUTH_ID, principal.getId()))
                .addClaims(Map.of(AUTH_USERNAME, principal.getUsername()))
                .addClaims(Map.of(AUTH_KEY, authorities))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
        return jwt;
    }

    /** 권한이 필요한 Request에서 사용
     *  Token -(decode)-> claims 추출
     *  claims로 UsernamePasswordAuthenticationToken 발급
     */
    public Authentication getAuthenticationFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> authorities = Arrays.asList(claims.get(AUTH_KEY).toString().split(","));

        List<? extends GrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(auth -> new SimpleGrantedAuthority(auth)).collect(Collectors.toList());

        MemberDetails principal = new MemberDetails((String) claims.get(AUTH_ID),(String) claims.get(AUTH_USERNAME), null, simpleGrantedAuthorities);

        return new UsernamePasswordAuthenticationToken(principal, token, simpleGrantedAuthorities);
    }

    /** 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new ValidateTokenException();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new ValidateTokenException();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new ValidateTokenException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new ValidateTokenException();
        }
    }


}
