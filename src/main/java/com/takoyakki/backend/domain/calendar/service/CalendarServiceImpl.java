package com.takoyakki.backend.domain.calendar.service;

import com.takoyakki.backend.domain.calendar.dto.request.CalendarRequestDto;
import com.takoyakki.backend.domain.calendar.dto.response.*;
import com.takoyakki.backend.domain.calendar.repository.CalendarMapper;
import com.takoyakki.backend.domain.dailyLearning.repository.DailyLearningMapper;
import com.takoyakki.backend.domain.mentoring.repository.MentoringMapper;
import com.takoyakki.backend.domain.mentoring.repository.MentoringReservationMapper;
import com.takoyakki.backend.domain.todo.repository.TodosMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService{
    private final MentoringMapper mentoringMapper;
    private final MentoringReservationMapper mentoringReservationMapper;
    private final DailyLearningMapper dailyLearningMapper;
    private final TodosMapper todosMapper;
    private final CalendarMapper calendarMapper;

    @Override
    public Map<LocalDate, CalendarScheduleListResponseDto> selectScheduleAllByMonth(Long memberId, YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        Map<LocalDate, CalendarScheduleListResponseDto> resultMap = new LinkedHashMap<>();

        for (int i = 1; i <= month.lengthOfMonth(); i++) {
            LocalDate date = month.atDay(i);
            resultMap.put(date, new CalendarScheduleListResponseDto(date));
        }

        List<CalendarItemMentoringByDateDto> mentorings = mentoringMapper.selectMentoringListInMonthByMemberId(memberId, startDate, endDate);
        List<CalendarItemCurriculumByDateDto> curriculums = dailyLearningMapper.selectCurriculumInMonthByMemberId(startDate, endDate);
        List<CalendarItemTodoByDateDto> todos = todosMapper.selectTodoListInMonthByMemberId(memberId, startDate, endDate);
        List<CalendarItemMentoringReservationByDateDto> mentoringsReservations = mentoringReservationMapper.selectMentoringReservationListInMonthByMemberId(memberId, startDate, endDate);


        for (CalendarItemMentoringByDateDto m : mentorings) {
            resultMap.get(m.getDate()).getMentoringList().add(m);
        }

        for (CalendarItemCurriculumByDateDto c : curriculums) {
            resultMap.get(c.getDate()).getSubjectList().add(c.getSubject());
        }

        for (CalendarItemTodoByDateDto t : todos) {
            resultMap.get(t.getDate()).getTodoList().add(t);
        }

        for (CalendarItemMentoringReservationByDateDto r : mentoringsReservations) {
            resultMap.get(r.getDate()).getMentoringReservationList().add(r);
        }

        return resultMap;
    }


// 일정목록 조회
    @Override
    public List<CalendarResponseDto> getSchedules(Long memberId, String startDate, String endDate) {
        try {
            return calendarMapper.selectSchedules(memberId, startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("스케쥴 조회 중 문제가 발생했습니다: " + e.getMessage(), e);
        }
    }

//  일정 상세조회
    @Override
    public CalendarResponseDto getScheduleDetail(Long scheduleId) {
        try {
            return calendarMapper.selectScheduleById(scheduleId);
        } catch (Exception e) {
            throw new RuntimeException("문제가 발생했습니다: " + e.getMessage(), e);
        }

    }

//   일정 등록
    @Override
    @Transactional
    public void addSchedule(CalendarRequestDto dto) {
        try {
            calendarMapper.insertSchedule(dto);
        } catch (Exception e) {
            throw new RuntimeException("문제가 발생했습니다: " + e.getMessage(), e);
        }

    }

//    일정 수정
    @Override
    @Transactional
    public void updateSchedule(Long scheduleId, CalendarRequestDto dto) {
        try {
            dto.setScheduleId(scheduleId);
            calendarMapper.updateSchedule(dto);
        } catch (Exception e) {
            throw new RuntimeException("문제가 발생했습니다: " + e.getMessage(), e);
        }

    }

//   일정 삭제
    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        try {
            calendarMapper.deleteSchedule(scheduleId);
        } catch (Exception e) {
            throw new RuntimeException("문제가 발생했습니다: " + e.getMessage(), e);
        }
    }
}

