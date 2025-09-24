package com.takoyakki.backend.domain.todo.service;

import com.takoyakki.backend.domain.todo.dto.TodoCreateRequestDto;
import com.takoyakki.backend.domain.todo.dto.TodoSelectResponseDto;
import com.takoyakki.backend.domain.todo.dto.TodoUpdateRequestDto;

import java.util.List;

public interface TodoService {
    List<TodoSelectResponseDto> getAllTodos(Long memberId, String date);

    Long createTodo(TodoCreateRequestDto request);

    boolean updateTodo(Long id, TodoUpdateRequestDto request);

    boolean softDeleteTodo(Long id);

    boolean updateTodoToggle(Long id);
}
