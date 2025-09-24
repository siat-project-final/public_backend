package com.takoyakki.backend.domain.challenge.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "AI 챌린지 랭킹 응답")
@Builder
@ToString
public class ChallengeRankResponseDto {
    @Schema(description = "유저 고유 id", example = "11")
    private Long memberId;

    @Schema(description = "유저 이름", example = "홍길동")
    private String memberName;

    @Schema(description = "총 점수", example = "15")
    private int totalPoints;

    @Schema(description = "랭킹", example = "1")
    private int rank;

    @Schema(description = "날짜", example = "2025-06-01")
    private LocalDate date;

    @Schema(description = "과목", example = "java")
    private String subject;
}
