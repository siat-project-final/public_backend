package com.takoyakki.backend.domain.todo.controller;

import com.takoyakki.backend.domain.todo.dto.TodoCreateRequestDto;
import com.takoyakki.backend.domain.todo.dto.TodoSelectResponseDto;
import com.takoyakki.backend.domain.todo.dto.TodoUpdateRequestDto;
import com.takoyakki.backend.domain.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/todos")
@Tag(name = "Todo", description = "투두리스트 관리 API")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(
            summary = "todo 전체 조회",
            description = "멤버 id와 날짜를 받아 todo 전체를 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버의 todo 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<List<TodoSelectResponseDto>> getAllTodos(
            @Parameter(description = "사용자 ID", required = true) @RequestParam Long memberId,
            @Parameter(description = "날짜 (YYYY-MM-DD)", required = false) @RequestParam(required = false) String date) {

        if (memberId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<TodoSelectResponseDto> todos = todoService.getAllTodos(memberId, date);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "todo 생성", description = "새로운 투두 항목을 생성하고 todoId를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 생성됨",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Long> createTodo(@Valid @RequestBody TodoCreateRequestDto request) {
        Long todoId = todoService.createTodo(request);
        return ResponseEntity.ok(todoId);
    }

    @Operation(summary = "todo 수정", description = "기존 투두 항목을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정됨", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateTodo(
            @Parameter(description = "투두 ID") @PathVariable Long id,
            @Valid @RequestBody TodoUpdateRequestDto request) {
        boolean success = todoService.updateTodo(id, request);
        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(Map.of("success", success));
    }

    @Operation(summary = "todo 토글", description = "투두의 체크 토글을 check/unchecked 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정됨", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PutMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Boolean>> toggleTodo(
            @Parameter(description = "투두 ID") @PathVariable Long id) {
        boolean success = todoService.updateTodoToggle(id);
        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(Map.of("success", success));
    }

    @Operation(summary = "todo 삭제", description = "기존 투두 항목을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 삭제됨", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> softDeleteTodo(
            @Parameter(description = "투두 ID") @PathVariable Long id) {
        boolean success = todoService.softDeleteTodo(id);
        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(Map.of("success", success));
    }

}