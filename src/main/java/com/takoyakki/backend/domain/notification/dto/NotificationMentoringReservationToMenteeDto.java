package com.takoyakki.backend.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "멘토링 예약 승인/거절 및 취소 알림 DTO")
@Builder
public class NotificationMentoringReservationToMenteeDto {
    @Schema(description = "알림 id", example = "1")
    private Long notificationId;

    @Schema(description = "제목", example = "멘토링 예약 거절")
    private String contents;

    @Schema(description = "내용", example = "멘토링 예약이 거절 되었습니다.")
    private String title;

    @Schema(description = "상태", example = "CANCELED")
    private String status;

    @Schema(description = "사유", example = "건강상의 이유")
    private String reason;
}
