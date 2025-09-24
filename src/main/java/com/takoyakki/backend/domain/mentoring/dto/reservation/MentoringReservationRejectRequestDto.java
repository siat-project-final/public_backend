package com.takoyakki.backend.domain.mentoring.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MentoringReservationRejectRequestDto {

    @Schema(description = "예약 거절 사유", example = "NO_TIME")
    @NotBlank(message = "거절 사유는 필수입니다.")
    private String rejectReason;
}
