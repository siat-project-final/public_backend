package com.takoyakki.backend.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "회원가입시 교육생 명단 존재 체크")
@Builder
public class SignUpAuthCheckDto {
    @Schema(description = "member_name")
    private String memberName;
    @Schema(description = "phone_number")
    private String phoneNumber;
}
