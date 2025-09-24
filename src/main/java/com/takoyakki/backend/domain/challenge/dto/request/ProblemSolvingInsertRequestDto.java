package com.takoyakki.backend.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "문제 풀이 제출 요청")
@Builder
public class ProblemSolvingInsertRequestDto {
    @Schema(description = "유저 고유 id", example = "11")
    private Long memberId;

    @Schema(description = "문제 id list", example = "[1, 2, 3, 4, 5]")
    private List<Long> problemIds;

    @Schema(description = "문제 답안 list", example = "[1, 2, 1, 4, 2]")
    private List<Integer> answers;

    @Schema(description = "문제 생성일", example = "2025-06-16T14:30:00")
    private LocalDateTime createdAt;
}
