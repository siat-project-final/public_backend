package com.takoyakki.backend.domain.todo.repository;

import com.takoyakki.backend.domain.todo.dto.TodoCreateRequestDto;
import com.takoyakki.backend.domain.todo.dto.TodoUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface TodoItemMapper {
    int insertTodoItem(TodoCreateRequestDto request);

    int updateTodoItem(@Param("id") Long id, @Param("request") TodoUpdateRequestDto request);

    int softDeleteTodoItem(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
}
