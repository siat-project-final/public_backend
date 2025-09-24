package com.takoyakki.backend.domain.studyDiary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DiaryCommentsUpdateRequestDto {
    @NotNull
    private Long memberId;

    @NotBlank
    private String contents;
}

