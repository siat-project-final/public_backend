package com.takoyakki.backend.domain.mentoring.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//멘토가 예약을 수락했을때, 서버-> 클라이언트로 전달되는 응답
@Data
public class MentoringReservationAcceptResponseDto {
    private Long reservationId; // 예약 ID
    private String mentorName; // 멘토 이름
    private String mentorImageUrl; // 멘토 프로필 이미지 URL
    private LocalDateTime date; // 예약 시간 (YYYY-MM-DD HH:mm)
    private LocalDateTime createdAt;
    private String openChatUrl; // 오픈채팅 URL
}
