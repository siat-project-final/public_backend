package com.takoyakki.backend.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "할 일 조회 응답 DTO")
public class TodoSelectResponseDto {

    @Schema(description = "투두 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 ID", example = "member123")
    private String memberId;

    @Schema(description = "할 일 내용", example = "스터디 자료 정리")
    private String contents;

    @Schema(description = "할 일 날짜", example = "2025-06-18")
    private String date;

    @Schema(description = "체크 여부", example = "true")
    private boolean isChecked;

    @Schema(description = "생성일시", example = "2025-06-18T14:00:00")
    private String createdAt;

    @Schema(description = "수정일시", example = "2025-06-18T15:00:00")
    private String updatedAt;
}
