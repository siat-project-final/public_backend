package com.takoyakki.backend.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Schema(description = "로그인 성공")
@Builder
public class LoginResponseDto {
    @Schema(description = "member_id")
    private Long memberId;

    @Schema(description = "access token")
    private String accessToken;
    @Schema(description = "refresh token")
    private String refreshToken;
    @Schema(description = "성공 메세지")
    private String message;

    @Schema(description = "id")
    private String id;
    @Schema(description = "role")
    private String role;
    @Schema(description = "member_name")
    private String memberName;
}
