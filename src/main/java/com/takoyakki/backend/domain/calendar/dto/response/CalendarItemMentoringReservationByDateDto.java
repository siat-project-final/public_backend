package com.takoyakki.backend.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "캘린더 출력을 위한 dto 작성을 위한 멘토링 예약 아이템 dto")
@Builder
@ToString
public class CalendarItemMentoringReservationByDateDto {
    @Schema(description = "날짜", example = "2025-06-21")
    private LocalDate date;

    @Schema(description = "멘토링 예약 id", example = "1")
    private Long reservationId;

    @Schema(description = "멘토 이름", example = "홍길동")
    private String mentorName;

}
