package com.takoyakki.backend.common.auth;

import com.takoyakki.backend.common.auth.dto.LoginAuthCheckDto;
import com.takoyakki.backend.common.auth.dto.LoginResponseDto;
import com.takoyakki.backend.common.auth.mapper.AuthMapper;
import com.takoyakki.backend.common.auth.service.RedisService;
import com.takoyakki.backend.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    private final RedisService redisService;
    private final AuthMapper authMapper;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
    }

    public TokenInfo createToken(LoginAuthCheckDto loginAuthCheckDto) {
        // Access Token 생성
        String accessToken = createAccessToken(loginAuthCheckDto);
        // Refresh Token 생성
        String refreshToken = createRefreshToken(loginAuthCheckDto);

        // Redis에 Refresh Token 저장
        redisService.saveRefreshToken(
                loginAuthCheckDto.getId(),
                refreshToken,
                60 * 60 * 24 * 14 // 14일 (초 단위)
        );

        return new TokenInfo(accessToken, refreshToken);
    }


    public String createAccessToken(LoginAuthCheckDto loginAuthCheckDto) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");

        Long expTime = 1000 * 60L * 3L;
        Date ext = new Date();
        ext.setTime(ext.getTime() + expTime);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", loginAuthCheckDto.getId());
        payload.put("memberName", loginAuthCheckDto.getMemberName());
        payload.put("role", loginAuthCheckDto.getRole());

        return Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setSubject("access")
                .setExpiration(ext)
                .signWith(key)
                .compact();
    }

    private String createRefreshToken(LoginAuthCheckDto loginAuthCheckDto) {
        Long refreshExpTime = 1000 * 60L * 60L * 24L * 14L; // 14일
        Date refreshExt = new Date();
        refreshExt.setTime(refreshExt.getTime() + refreshExpTime);

        return Jwts.builder()
                .setSubject(loginAuthCheckDto.getId())
                .setExpiration(refreshExt)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    public String reissueAccessToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            String id = claims.getSubject();

            // Redis에서 저장된 리프레시 토큰 조회
            String savedRefreshToken = redisService.getRefreshToken(id);
            if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
                throw new JwtException("Invalid refresh token");
            }

            LoginAuthCheckDto loginAuthCheckDto = Optional.ofNullable(authMapper.selectUserInfo(id))
                    .orElseThrow(() -> new JwtException("Member not found"));

            loginAuthCheckDto.setId(id);
            return createAccessToken(loginAuthCheckDto);
        } catch (JwtException e) {
            log.error("Error during access token reissue: {}", e.getMessage(), e);
            throw new JwtException("Failed to reissue access token");
        }
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (String) claims.get("id");
    }

    public String getMemberName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (String) claims.get("memberName");
    }

    @SuppressWarnings("unchecked")
    public List<String> getRole(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Object positionObj = claims.get("role");
        if (positionObj instanceof String) {
            return Collections.singletonList((String) positionObj);
        } else if (positionObj instanceof List) {
            return (List<String>) positionObj;
        }
        return Collections.emptyList();
    }

    public void removeRefreshToken(String id) {
        redisService.deleteRefreshToken(id);
    }

}