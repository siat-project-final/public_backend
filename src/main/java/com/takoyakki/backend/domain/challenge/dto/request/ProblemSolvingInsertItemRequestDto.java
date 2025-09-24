package com.takoyakki.backend.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "배치 insert 실행을 위한 변환용 dto")
@Builder
public class ProblemSolvingInsertItemRequestDto {
    @Schema(description = "유저 고유 id", example = "11")
    private Long memberId;

    @Schema(description = "문제 id", example = "1")
    private Long problemId;

    @Schema(description = "문제 생성일", example = "2025-06-16T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "답안", example = "1")
    private int answer;

    @Schema(description = "정답 여부", example = "true")
    private boolean isCorrect;

    @Schema(description = "점수", example = "2")
    private int points;
}
