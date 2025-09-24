package com.takoyakki.backend.domain.studyDiary.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DiaryCommentsDeleteRequestDto {
    private Long memberId;
}
