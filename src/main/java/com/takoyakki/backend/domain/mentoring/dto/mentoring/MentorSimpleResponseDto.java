package com.takoyakki.backend.domain.mentoring.dto.mentoring;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorSimpleResponseDto {
    private Long mentorId;
    private String mentorName;
    private String position;
    private String company;
    private String mentorImageUrl;
    private String mentorMemberId;
}
