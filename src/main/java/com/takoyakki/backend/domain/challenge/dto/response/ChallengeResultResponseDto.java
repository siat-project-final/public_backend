package com.takoyakki.backend.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "챌린지 결과 응답")
public class ChallengeResultResponseDto {

    @Schema(description = "이번 제출로 획득한 XP", example = "20")
    private int xp;

    @Schema(description = "누적 포인트", example = "150")
    private int point;

}
