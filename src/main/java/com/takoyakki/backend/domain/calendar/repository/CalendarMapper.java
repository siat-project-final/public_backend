package com.takoyakki.backend.domain.calendar.repository;

import com.takoyakki.backend.domain.calendar.dto.request.CalendarRequestDto;
import com.takoyakki.backend.domain.calendar.dto.response.CalendarResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CalendarMapper {

    List<CalendarResponseDto> selectSchedules(@Param("memberId") Long memberId,
                                              @Param("startDate") String startDate,
                                              @Param("endDate") String endDate);

    CalendarResponseDto selectScheduleById(@Param("scheduleId") Long scheduleId);

    int insertSchedule(CalendarRequestDto dto);

    int updateSchedule(CalendarRequestDto dto);

    int deleteSchedule(@Param("scheduleId") Long scheduleId);
}

