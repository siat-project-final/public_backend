package com.takoyakki.backend.common.interceptor;

import com.takoyakki.backend.common.auth.CheckAuthority;
import com.takoyakki.backend.common.auth.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // OPTIONS 요청은 통과
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        // 컨트롤러 메서드가 아닌 경우는 패스
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        /*
        인증
         */

        System.out.println("auth filter~~~~~~~~~~~~~~");

        // 토큰 추출
        String token = jwtTokenProvider.extractToken(request);
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
            return false;
        }

        // 토큰 검증
        if (!jwtTokenProvider.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return false;
        }

        /*
        인가
         */

        CheckAuthority checkAuthority = handlerMethod.getMethodAnnotation(CheckAuthority.class);
        if (checkAuthority == null) {
            return true;
        }

        // 토큰에서 사용자명 또는 ID 추출
        String id = jwtTokenProvider.getId(token);
        if (id == null) {
            log.warn("토큰에서 사용자 정보를 추출할 수 없습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자 정보를 확인할 수 없습니다.");
            return false;
        }

        // 권한 체크
        List<String> userRole = jwtTokenProvider.getRole(token);
        if (userRole == null || userRole.isEmpty()) {
            log.warn("사용자 권한 정보가 존재하지 않습니다.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한 정보가 없습니다.");
            return false;
        }

        // 어노테이션 루프
        boolean hasAuthority = false;
        for (String requiredRole : checkAuthority.value()) {
            if (userRole.contains(requiredRole)) {
                hasAuthority = true;
                break;
            }
        }

        if (!hasAuthority) {
            log.warn("사용자 {}에게 필요한 권한이 없습니다. Required: {}", id, checkAuthority.value());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
            return false;
        }

        return true;
    }
}
