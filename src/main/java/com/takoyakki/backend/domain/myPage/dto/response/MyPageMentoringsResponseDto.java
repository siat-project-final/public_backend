package com.takoyakki.backend.domain.myPage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "마이페이지 멘토링 히스토리 응답")
@Builder
public class MyPageMentoringsResponseDto {
    @Schema(description = "멘토 id", example = "1")
    private Long mentorId;

    @Schema(description = "멘토 이름", example = "홍길동")
    private String mentorName;

    @Schema(description = "멘토 이미지 url", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "대화 주제", example = "JAVA")
    private String subject;

    @Schema(description = "날짜", example = "2023-10-01")
    private LocalDate date;
}
