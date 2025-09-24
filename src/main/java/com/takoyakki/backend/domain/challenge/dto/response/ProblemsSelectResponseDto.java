package com.takoyakki.backend.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "문제 단건 조회 응답")
@Builder
public class ProblemsSelectResponseDto {
    @Schema(description = "문제 id", example = "1")
    private Long problemId;

    @Schema(description = "문제 제목", example = "Java에서 기본 데이터 타입(Primitive Type)은?")
    private String title;

    @Schema(description = "답", example = "1")
    private int answer;

    @Schema(description = "배점", example = "1")
    private int points;

    @Schema(description = "문제", example = "Java의 기본 데이터 타입은?")
    private String contents;

    @Schema(description = "과목", example = "JAVA")
    private String subject;

    @Schema(description = "선택지 리스트", example = "[\"int\", \"String\", \"float\", \"double\"]")
    private String choices;
}
