package com.takoyakki.backend.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "캘린더 일정 전체 조회 응답")
@ToString
public class CalendarScheduleListResponseDto {
    @Schema(description = "날짜", example = "2025-06-21")
    private LocalDate date;

    // 아래 멤버들은 각 날짜에 해당하는 데이터를 리스트에 담아 반환
    @Schema(description = "커리큘럼 과목", example = "JAVA")
    private List<String> subjectList;

    @Schema(description = "학습 일지", example = "JAVA 학습일지")
    private List<CalendarItemStudyDiaryByDateDto> studyDiaryList;

    @Schema(description = "멘토링", example = "멘토 홍길동, 과목 JAVA 기초")
    private List<CalendarItemMentoringByDateDto> mentoringList;

    @Schema(description = "멘토링 예약", example = "멘토 홍길동, 과목 JAVA 기초")
    private List<CalendarItemMentoringReservationByDateDto> mentoringReservationList;

    @Schema(description = "todo 리스트", example = "JS 복습, 알고리즘 특강, sqld 자격증 공부")
    private List<CalendarItemTodoByDateDto> todoList;

    public CalendarScheduleListResponseDto(LocalDate date) {
        this.date = date;
        this.subjectList = new ArrayList<>();
        this.studyDiaryList = new ArrayList<>();
        this.mentoringList = new ArrayList<>();
        this.mentoringReservationList = new ArrayList<>();
        this.todoList = new ArrayList<>();
    }
}
