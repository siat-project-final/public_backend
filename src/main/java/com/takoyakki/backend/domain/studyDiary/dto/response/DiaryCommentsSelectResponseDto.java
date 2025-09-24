package com.takoyakki.backend.domain.studyDiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "학습 일지 댓글 전체 조회 응답")
@Builder
public class DiaryCommentsSelectResponseDto {

    @Schema(description = "댓글 ID", example = "1")
    @NotNull
    private Long commentId;

    @Schema(description = "이름", example = "홍길동")
    private String memberName;

    @Schema(description = "댓글 내용", example = "깔끔하게 정리했네요:) 덕분에 많은 도움 받고 갑니다아")
    private String contents;

    @Schema(description = "작성일", example = "2025-06-19")
    private LocalDate date;
}
