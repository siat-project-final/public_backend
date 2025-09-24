package com.takoyakki.backend.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "챌린지 포인트 보상 알림 DTO")
@Builder
public class NotificationChallengeToMenteeDto {
    @Schema(description = "알림 id", example = "1")
    private Long notificationId;

    @Schema(description = "멤버 id", example = "1")
    private Long memberId;

    @Schema(description = "제목", example = "챌린지 랭크 보상이 지급되었습니다.")
    private String title;

    @Schema(description = "내용", example = "데일리 챌린지 결과 랭크에 따라 포인트가 지급되었습니다.")
    private String contents;

}
