package com.takoyakki.backend.domain.studyDiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "학습 일지 조회 요청")
@Builder
public class StudyDiaryAISummaryRequestDto {
    @Schema(description = "전체 내용", example = "Java는 OOP 언어로, 객체 지향 프로그래밍의 특징을 갖고 있습니다. 주요 개념으로는 클래스, 객체, 상속, 다형성 등이 있습니다.")
    private String text;
}
