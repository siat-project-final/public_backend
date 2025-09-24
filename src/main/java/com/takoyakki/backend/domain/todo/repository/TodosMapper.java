package com.takoyakki.backend.domain.todo.repository;

import com.takoyakki.backend.domain.calendar.dto.response.CalendarItemMentoringByDateDto;
import com.takoyakki.backend.domain.calendar.dto.response.CalendarItemTodoByDateDto;
import com.takoyakki.backend.domain.todo.dto.TodoCreateRequestDto;
import com.takoyakki.backend.domain.todo.dto.TodoSelectResponseDto;
import com.takoyakki.backend.domain.todo.dto.TodoUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TodosMapper {
    List<TodoSelectResponseDto> selectAllTodos(@Param("memberId") Long memberId, @Param("date") String date);

    Optional<TodoSelectResponseDto> selectTodoById(@Param("id") Long id);

    int insertTodo(TodoCreateRequestDto request);

    int updateTodo(@Param("id") Long id, @Param("request") TodoUpdateRequestDto request);

    int softDeleteTodo(@Param("id") Long id);

    List<CalendarItemTodoByDateDto> selectTodoListInMonthByMemberId(Long memberId, LocalDate startDate, LocalDate endDate);

    int updateTodoToggle(Long id);

}