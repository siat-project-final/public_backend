package com.takoyakki.backend.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "캘린더 출력을 위한 dto 작성을 위한 학습일지 아이템 dto")
@Builder
@ToString
public class CalendarItemStudyDiaryByDateDto {
    @Schema(description = "날짜", example = "2025-06-21")
    private LocalDate date;

    @Schema(description = "학습일지 id", example = "1")
    private String diaryId;

    @Schema(description = "제목", example = "JAVA 학습")
    private String title;

}
