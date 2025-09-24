package com.takoyakki.backend.domain.notification.repository;

import com.takoyakki.backend.domain.mentoring.dto.reservation.MenteeReservationRequestDto;
import com.takoyakki.backend.domain.notification.dto.NotificationChallengeToMenteeDto;
import com.takoyakki.backend.domain.notification.dto.NotificationMentoringReservationToMenteeDto;
import com.takoyakki.backend.domain.notification.dto.response.NotificationToMenteeSelectResponseDto;
import com.takoyakki.backend.domain.notification.dto.response.NotificationToMentorSelectResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    // 멘티
    int insertNotificationMentoringReservationToMentor(MenteeReservationRequestDto dto);

    int insertAcceptNotificationMentoringReservationToMentee(Long memberId, Long reservationId, String contents);

    int insertRejectNotificationMentoringReservationToMentee(Long memberId, Long reservationId, String contents);

    List<NotificationToMenteeSelectResponseDto> selectNotificationToMentee(Long memberId);

    // 멘토
    int insertCancelNotificationMentoringReservationToMentor(Long memberId, Long reservationId, String contents);

    List<NotificationToMentorSelectResponseDto> selectNotificationToMentor(Long memberId);

    // 챌린지
    int insertNotificationChallengeRankPointsToMentee(NotificationChallengeToMenteeDto dto);

    boolean softDeleteNotification(Long notificationId);
}
