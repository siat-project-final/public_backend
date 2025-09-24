package com.takoyakki.backend.domain.todo.repository;

import com.takoyakki.backend.domain.todo.dto.TodoSelectResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoDateMapper {
    List<TodoSelectResponseDto> selectTodosByDate(@Param("memberId") String memberId, @Param("date") String date);

    Optional<TodoSelectResponseDto> selectTodoById(@Param("id") Long id);
}