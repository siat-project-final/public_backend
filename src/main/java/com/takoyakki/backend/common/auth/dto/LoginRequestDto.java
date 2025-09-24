package com.takoyakki.backend.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Schema(description = "로그인 요청")
@Builder
public class LoginRequestDto {

    @NotBlank
    @Schema(description = "id")
    private String id;

    @NotBlank
    @Schema(description = "password")
    private String password;
}
