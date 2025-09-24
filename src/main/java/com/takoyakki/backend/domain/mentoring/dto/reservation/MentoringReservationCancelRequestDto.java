package com.takoyakki.backend.domain.mentoring.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;


 //멘토링 예약을 취소할 때 사용하는 요청 DTO.
 //멘티 또는 멘토가 예약 상태를 '취소'로 변경할 때 사용됨

@Data
public class MentoringReservationCancelRequestDto {

    @Schema(description = "취소 사유", example = "PERSONAL_REASON")
    @NotBlank(message = "취소 사유는 필수입니다.")
    private String cancelReason;

}

