package com.takoyakki.backend.domain.mentoring.service;

import com.takoyakki.backend.domain.mentoring.dto.reservation.*;

import java.util.List;

public interface MentoringReservationService {

    void createReservation(MenteeReservationRequestDto requestDto);

    List<MentoringReservationResponseDto> getReservationsByMenteeId(Long menteeId);

    List<MentoringReservationResponseDto> getReservationsByMentorId(Long mentorId);

    void acceptReservation(Long reservationId);

    void rejectReservation(Long reservationId, MentoringReservationRejectRequestDto decisionDto);

    void cancelReservation(Long reservationId, MentoringReservationCancelRequestDto cancelDto);

    void hideReservationByMentee(Long reservationId);

    void hideReservationByMentor(Long reservationId);

    void cancelReservationMentor(Long reservationId, MentoringReservationCancelRequestDto cancelDto);
}
