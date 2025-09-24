package com.takoyakki.backend.domain.studyDiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "학습 일지 조회 응답")
// @Builder
public class StudyDiarySelectResponseDto {

    @Schema(description = "학습 일지 ID", example = "10")
    private Long diaryId;

    @Schema(description = "회원 ID", example = "123")
    private Long memberId;

    @Schema(description = "회원 이름", example = "홍길동")
    private String memberName;

    @Schema(description = "제목", example = "Java")
    private String title;

    @Schema(description = "내용", example = "Advice, Pointcut 등 정리")
    private String contents;

    @Schema(description = "주제명", example = "Spring")
    private String subject;

    @Schema(description = "AI 요약", example = "AOP 개념 요약")
    private String aiSummary;

    @Schema(description = "학습 날짜", example = "2025-06-19")
    private String studyDate;

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPublic;

    @Schema(description = "생성일시", example = "2025-06-19T14:30:00")
    private String createdAt;

    @Schema(description = "수정일시", example = "2025-06-20T09:10:00")
    private String updatedAt;
}
