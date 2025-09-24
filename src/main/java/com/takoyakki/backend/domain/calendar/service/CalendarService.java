package com.takoyakki.backend.domain.calendar.service;

import com.takoyakki.backend.domain.calendar.dto.request.CalendarRequestDto;
import com.takoyakki.backend.domain.calendar.dto.response.CalendarResponseDto;
import com.takoyakki.backend.domain.calendar.dto.response.CalendarScheduleListResponseDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface CalendarService {
    Map<LocalDate, CalendarScheduleListResponseDto> selectScheduleAllByMonth(Long memberId, YearMonth month);


    List<CalendarResponseDto> getSchedules(Long memberId, String startDate, String endDate);

    CalendarResponseDto getScheduleDetail(Long scheduleId);

    void addSchedule(CalendarRequestDto dto);

    void updateSchedule(Long scheduleId, CalendarRequestDto dto);

    void deleteSchedule(Long scheduleId);
}


