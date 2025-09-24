package com.takoyakki.backend.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Schema(description = "로그인 인증 체크")
@Builder
@ToString
public class LoginAuthCheckDto {
    @Schema(description = "id")
    private String id;
    @Schema(description = "password")
    private String password;
    @Schema(description = "role")
    private String role;
    @Schema(description = "member_id")
    private Long memberId;
    @Schema(description = "member_name")
    private String memberName;

}
