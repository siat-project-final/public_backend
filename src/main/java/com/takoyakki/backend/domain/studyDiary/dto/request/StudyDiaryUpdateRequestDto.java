package com.takoyakki.backend.domain.studyDiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "학습 일지 수정 요청")
@Builder
public class StudyDiaryUpdateRequestDto {

    @Schema(description = "학습일지 ID", example = "10")
    private Long diaryId;

    @Schema(description = "제목", example = "Java")
    private String title;

    @Schema(description = "내용", example = "OOP 개념 정리")
    private String contents;

    @Schema(description = "과목", example = "JAVA")
    private String subject;

    @Schema(description =" 학습날짜",example="2025-06-19")
    private String studyDate;

    @Schema(description = "AI 요약", example = "Java OOP의 핵심 개념 요약")
    private String aiSummary;

    @Schema(description = "공개여부", example = "true")
    private Boolean ispublic;
}