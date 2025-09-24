package com.takoyakki.backend.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "문제 풀이 제출시 사용되는 응답 DTO")
@Builder
public class ProblemSolvingSubmitResponseDto {
    @Schema(description = "난이도 및 배점", example = "1")
    private int difficulty;

    @Schema(description = "답", example = "1")
    private int answer;
}
