package com.takoyakki.backend.domain.calendar.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CalendarResponseDto {
    private Long scheduleId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private Boolean isAllDay;
    private String colorCode;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
