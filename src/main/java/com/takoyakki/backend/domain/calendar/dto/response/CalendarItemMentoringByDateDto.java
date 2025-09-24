package com.takoyakki.backend.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "캘린더 출력을 위한 dto 작성을 위한 멘토링 아이템 dto")
@Builder
@ToString
public class CalendarItemMentoringByDateDto {
    @Schema(description = "날짜", example = "2025-06-21")
    private LocalDate date;

    @Schema(description = "멘토링 id", example = "1")
    private Long mentoringId;

    @Schema(description = "멘토 이름", example = "홍길동")
    private String mentorName;

}
