package com.takoyakki.backend.domain.studyDiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "학습 일지 댓글 입력 요청")
@Builder
public class DiaryCommentsInsertRequestDto {
    @Schema(description = "멤버 id", example = "1")
    private Long memberId;

    @Schema(description = "다이어리 id", example = "1")
    private Long diaryId;

    @Schema(description = "내용", example = "깔끔하게 정리했네요:) 덕분에 많은 도움 받고 갑니다아")
    private String contents;
}
