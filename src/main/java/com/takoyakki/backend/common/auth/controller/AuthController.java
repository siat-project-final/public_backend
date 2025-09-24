package com.takoyakki.backend.common.auth.controller;

import com.takoyakki.backend.common.auth.JwtTokenProvider;
import com.takoyakki.backend.common.auth.dto.SignUpRequestDto;
import com.takoyakki.backend.common.auth.service.AuthService;
import com.takoyakki.backend.common.auth.service.MentorQueryService;
import com.takoyakki.backend.common.auth.dto.LoginRequestDto;
import com.takoyakki.backend.common.auth.dto.LoginResponseDto;
import com.takoyakki.backend.common.exception.TokenExpiredException;
import com.takoyakki.backend.common.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name = "인증", description = "인증 관련 API")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    // 필드 추가
    private final MentorQueryService mentorQueryService;
    
    @Operation(
            summary = "회원가입",
            description = "최초 접속시 회원가입을 수행합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        int response = authService.signUp(requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "로그인",
            description = "멤버 계정과 비밀번호로 로그인을 수행합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .header("Refresh-Token", response.getRefreshToken())
                .body(response);
    }

    @Operation(
            summary = "액세스 토큰 만료 체크",
            description = "현재 액세스 토큰이 만료되었는지 체크합니다"
    )
    @PostMapping("/checkAccessToken")
    public ResponseEntity<?> checkAccessToken(@RequestHeader("Authorization") String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다"
    )
    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(@RequestHeader("Refresh-Token") String refreshToken) {
        String newAccessToken = authService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .body("토큰이 재발급되었습니다.");
    }

    @Operation(
            summary = "로그아웃",
            description = "사용자 로그아웃을 수행합니다"
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String id = jwtTokenProvider.getId(token.replace("Bearer ", ""));
        authService.logout(id);
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    @Operation(
        summary = "멘토 ID 조회",
        description = "memberId로 mentors 테이블에서 mentorId를 조회합니다 (MENTOR 전용)"
    )
    @GetMapping("/mentor-id")
    public ResponseEntity<Long> getMentorId(@RequestParam Long memberId) {
        Long mentorId = mentorQueryService.getMentorIdByMemberId(memberId);
        return ResponseEntity.ok(mentorId);
    }
}
