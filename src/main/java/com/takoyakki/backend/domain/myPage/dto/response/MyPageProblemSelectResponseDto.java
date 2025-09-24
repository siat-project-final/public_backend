package com.takoyakki.backend.domain.myPage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "마이페이지 문제 풀이 상세 응답")
public class MyPageProblemSelectResponseDto {
    @Schema(description = "난이도", example = "1")
    private int difficulty;

    @Schema(description = "문제 제목", example = "JAVA: 1")
    private String title;

    @Schema(description = "문제 내용", example = "JAVA의 기본 자료형은 무엇인가요?")
    private String content;

    @Schema(description = "제출한 답", example = "1")
    private int submitAnswer;

    @Schema(description = "정답", example = "2")
    private int correctAnswer;

    @Schema(description = "정답 여부", example = "true")
    private boolean isCorrect;

    @Schema(description = "획득 점수", example = "1")
    private int points;
}
