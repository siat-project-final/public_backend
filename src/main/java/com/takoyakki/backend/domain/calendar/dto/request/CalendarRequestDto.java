package com.takoyakki.backend.domain.calendar.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Schema(description = "일정 등록/수정 요청")
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequestDto {

    @NotNull
    @Schema(description = "일정 ID (수정 시 필요)", example = "1")
    private Long scheduleId ;

    @NotNull
    @Schema(description = "회원 ID", example = "3")
    private Long memberId;

    @Schema(description = "일정 제목", example = "스터디 모임")
    private String title;

    @Schema(description = "일정 내용", example = "자바 스터디 모임 진행")
    private String content;

    @Schema(description = "시작 일시", example = "2025-07-05T10:00:00")
    private LocalDateTime startDatetime;

    @Schema(description = "종료 일시", example = "2025-07-05T12:00:00")
    private LocalDateTime endDatetime;

    @Schema(description = "하루 종일 여부", example = "false")
    private Boolean isAllDay;

    @Schema(description = "색상 코드", example = "#FF5733")
    private String colorCode;
}
