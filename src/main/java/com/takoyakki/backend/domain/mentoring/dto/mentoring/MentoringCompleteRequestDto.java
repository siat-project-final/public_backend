package com.takoyakki.backend.domain.mentoring.dto.mentoring;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 멘토링 완료 요청 시 사용하는 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringCompleteRequestDto {

    private Long mentoringReservationId;               // 예약 ID
    private Long mentorMemberId;                // 멘토 id
    private Long mentorId;                // 멘토 id
    private Long menteeId;                //멘티 id
    private LocalDate createdAt;           // 멘토링 완료된 날짜
}
