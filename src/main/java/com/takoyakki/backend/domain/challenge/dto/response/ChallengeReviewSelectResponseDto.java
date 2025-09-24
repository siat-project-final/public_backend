package com.takoyakki.backend.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "AI 챌린지 복습 응답")
@Builder
@ToString
public class ChallengeReviewSelectResponseDto {
    @Schema(description = "과목", example = "JAVA")
    private String subject;

    @Schema(description = "진도율", example = "100")
    private String progressRate;
}
