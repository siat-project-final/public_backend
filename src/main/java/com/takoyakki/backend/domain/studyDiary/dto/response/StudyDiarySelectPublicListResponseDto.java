package com.takoyakki.backend.domain.studyDiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "학습 일지 조회 응답")
@Builder
public class StudyDiarySelectPublicListResponseDto {

    @Schema(description = "학습 일지 ID", example = "10")
    private Long diaryId;

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
    private LocalDate studyDate;

    @Schema(description = "좋아요 수", example = "15")
    private Integer likeCount;
}

