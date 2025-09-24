package com.takoyakki.backend.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "할 일 수정 요청 DTO")
public class TodoUpdateRequestDto {
    @NotBlank(message = "내용은 필수입니다.")
    @Schema(description = "할 일 내용")
    private String contents;

    @Schema(description = "체크 여부", example = "true")
    private boolean isChecked;
}