package com.takoyakki.backend.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Data
@Schema(description = "중복 회원가입 체크")
@Builder
public class SignUpDuplicationCheckDto {
    @NotBlank
    @Schema(description = "member_name")
    private String memberName;

    @NotBlank
    @Schema(description = "phone_number")
    private String phoneNumber;
}
