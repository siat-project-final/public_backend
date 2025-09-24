package com.takoyakki.backend.domain.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "멘티 알림 응답 DTO")
@Builder
public class NotificationToMenteeSelectResponseDto {
    @Schema(description = "알림 id", example = "1")
    private Long notificationId;

    @Schema(description = "타입", example = "CHALLENGE")
    private String type;

    @Schema(description = "제목", example = "챌린지 랭크 보상이 지급되었습니다.")
    private String title;

    @Schema(description = "내용", example = "데일리 챌린지 결과 랭크에 따라 포인트가 지급되었습니다.")
    private String contents;

    @Schema(description = "생성일시", example = "2023-10-01T12:00:00")
    private LocalDateTime createdAt;

}
