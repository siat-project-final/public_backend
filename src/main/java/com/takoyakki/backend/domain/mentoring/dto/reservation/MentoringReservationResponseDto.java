package com.takoyakki.backend.domain.mentoring.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

// 멘토링 예약 상태를 멘티 , 멘토에게 보여줄때,
// 예약 목록 조회용
@Data
public class MentoringReservationResponseDto {

    @Schema(description = "예약 ID", example = "1001")
    private Long reservationId;

    @Schema(description = "멘토 id", example = "이수현")
    private Long mentorId;

    @Schema(description = "멘토 이름", example = "홍길동")
    private String mentorName;

    @Schema(description = "멘티 id", example = "1")
    private Long menteeId;

    @Schema(description = "멘티 이름", example = "홍길동")
    private String menteeName;

    @Schema(description = "멘토 프로필 이미지 URL", example = "https://example.com/mentor.jpg")
    private String mentorImageUrl;

    @Schema(description = "예약된 날짜 및 시간 (YYYY-MM-DD HH:mm)", example = "2023-10-01 14:00")
    private LocalDateTime date;

    @Schema(description = "예약 상태", example = "waiting")
    private String status;

    @Schema(description = "오픈채팅 URL", example = "https://open.kakao.com/o/g1a2b3c4")
    private String openChatUrl;

    @Schema(description = "멘토링 주제", example = "Spring 심화")
    private String subject;

    @Schema(description = "멘토 멤버 id", example = "1")
    private Long mentorMemberId;

    @Schema(description = "멘티가 숨긴 예약 여부", example = "false")
    private Boolean isHiddenByMentee;
}
