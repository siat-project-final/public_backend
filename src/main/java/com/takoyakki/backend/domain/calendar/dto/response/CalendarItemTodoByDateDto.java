package com.takoyakki.backend.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "캘린더 출력을 위한 dto 작성을 위한 투두 아이템 dto")
@Builder
@ToString
public class CalendarItemTodoByDateDto {
    @Schema(description = "날짜", example = "2025-06-21")
    private LocalDate date;

    @Schema(description = "todo id", example = "1")
    private String todoId;

    @Schema(description = "내용", example = "JAVA 복습하기")
    private String contents;

    @Schema(description = "체크 여부", example = "true")
    private boolean isChecked;

}
