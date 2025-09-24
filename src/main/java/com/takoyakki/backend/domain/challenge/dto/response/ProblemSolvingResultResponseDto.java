package com.takoyakki.backend.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "문제 풀이 결과 응답 DTO")
@Builder
public class ProblemSolvingResultResponseDto {
    @Schema(description = "문제 ID", example = "5")
    private Long problemId;

    @Schema(description = "문제 제목", example = "Spring에서 의존성 주입 방식이 아닌 것은?")
    private String title;

    @Schema(description = "난이도 및 배점", example = "2")
    private int difficulty;

    @Schema(description = "정답 (문자열)", example = "서비스 주입")
    private Integer correctAnswer;

    @Schema(description = "제출한 답 (문자열)", example = "필드 주입")
    private String submitAnswer;

    @Schema(description = "정답 여부", example = "false")
    private boolean correct;

    @Schema(description = "선택지", example = "1.생성자 주입\n2. 필드 주입\n3. 서비스 주입\n4. 메소드 주입")
    private String options;

    @Schema(description = "문제 유형 (multiple/text)")
    private String type;
}
