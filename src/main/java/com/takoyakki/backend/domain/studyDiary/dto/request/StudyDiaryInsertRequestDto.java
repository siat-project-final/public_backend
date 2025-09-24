package com.takoyakki.backend.domain.studyDiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "학습 일지 작성 요청")
public class StudyDiaryInsertRequestDto {

    @Schema(description = "회원 ID", example = "123")
    private Long memberId;

    @Schema(description = "제목", example = "Java")
    private String title;

    @Schema(description = "내용", example = "OOP 개념 정리")
    private String content;

    @Schema(description = "주제", example = "객체지향 프로그래밍")
    private String subject;

    @Schema(description = "AI 요약", example = "Java OOP의 핵심 개념 요약")
    private String summary;

    @Schema(description = "학습 날짜", example = "2025-06-19")
    private String date;

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPublic;
}
