package com.takoyakki.backend.domain.mentoring.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MentoringReservationHideRequestDto {
    @Schema(description = "숨기려는 예약 ID", example = "1001")
    @NotNull(message = "예약 ID는 필수입니다.")
    private Long reservationId;
}