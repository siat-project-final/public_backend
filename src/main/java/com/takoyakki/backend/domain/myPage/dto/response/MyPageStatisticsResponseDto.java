package com.takoyakki.backend.domain.myPage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "마이페이지 통계 응답")
public class MyPageStatisticsResponseDto {
    @Schema(description = "누적 학습일지 작성", example = "10")
    private int studyDiaryCount;

    @Schema(description = "챌린지 참여 횟수", example = "10")
    private int challengeCount;

    @Schema(description = "멘토링 참여 횟수", example = "10")
    private int mentoringCount;

    @Schema(description = "총 획득 포인트", example = "500")
    private int totalXp;
}
