package com.takoyakki.backend.domain.mentoring.service;

import com.takoyakki.backend.domain.mentoring.dto.reservation.*;
import com.takoyakki.backend.domain.mentoring.repository.MentoringReservationMapper;
import com.takoyakki.backend.domain.notification.repository.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoringReservationServiceImpl implements MentoringReservationService {

    private final MentoringReservationMapper reservationMapper;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void createReservation(MenteeReservationRequestDto requestDto) {
        try {
            // 테이블 insert
            reservationMapper.insertReservation(requestDto);

            // 멘토에게 알림 전송
            String contents = requestDto.getMenteeName() + "님께서 " +
                    requestDto.getDate().toLocalDate() + "에 멘토링을 신청하셨습니다. \n" +
                    "멘티 자기소개 : " + requestDto.getIntroduction() + "\n" +
                    "주제 : " + requestDto.getSubject() + "\n 멘토링 예약 페이지에서 확인해주세요.";
            requestDto.setNotificationContents(contents);
            notificationMapper.insertNotificationMentoringReservationToMentor(requestDto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<MentoringReservationResponseDto> getReservationsByMenteeId(Long menteeId) {
        try {
            return reservationMapper.selectReservationsByMenteeId(menteeId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<MentoringReservationResponseDto> getReservationsByMentorId(Long mentorId) {
        try {
            return reservationMapper.selectReservationsByMentorId(mentorId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public void acceptReservation(Long reservationId) {
        try {
            // 테이블 업데이트
            reservationMapper.updateReservationToAccepted(reservationId);

            // 멘티에게 알림 전송
            MentoringReservationResponseDto responseDto = reservationMapper.selectMentoringReservationInfoById(reservationId);
            String contents = responseDto.getMenteeName() + "님 \n" +
                    responseDto.getDate() + "에 예약된 " +
                    responseDto.getMentorName() + "님과의 멘토링이 확정되었습니다. \n" +
                    responseDto.getOpenChatUrl() + " 이 링크의 오픈채팅방에 참여해주세요.";
            notificationMapper.insertAcceptNotificationMentoringReservationToMentee(responseDto.getMenteeId(), reservationId, contents);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void rejectReservation(Long reservationId, MentoringReservationRejectRequestDto decisionDto) {
        try {
            // 테이블 업데이트
            reservationMapper.updateReservationToRejected(
                    reservationId,
                    decisionDto.getRejectReason()
            );

            // 멘티에게 알림 전송
            MentoringReservationResponseDto responseDto = reservationMapper.selectMentoringReservationInfoById(reservationId);
            String contents = responseDto.getMenteeName() + "님 \n" +
                    responseDto.getDate().toLocalDate() + "에 예약된 " +
                    responseDto.getMentorName() + "님과의 멘토링이 거절되었습니다. \n" +
                    "거절 사유: " + (decisionDto.getRejectReason());
            notificationMapper.insertRejectNotificationMentoringReservationToMentee(responseDto.getMenteeId(), reservationId, contents);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId, MentoringReservationCancelRequestDto cancelDto) {
        try {
            // 테이블 업데이트
            reservationMapper.cancelReservation(
                    reservationId,
                    cancelDto.getCancelReason()
            );

            // 멘토에게 알림 전송
            MentoringReservationResponseDto responseDto = reservationMapper.selectMentoringReservationInfoById(reservationId);
            String contents = responseDto.getMentorName() + "님 \n" +
                    responseDto.getDate().toLocalDate() + "에 예약된 " +
                    responseDto.getMentorName() + "님과의 멘토링이 취소되었습니다. \n" +
                    "취소 사유: " + (cancelDto.getCancelReason());
            notificationMapper.insertCancelNotificationMentoringReservationToMentor(responseDto.getMentorMemberId(), reservationId, contents);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void cancelReservationMentor(Long reservationId, MentoringReservationCancelRequestDto cancelDto) {
        try {
            // 테이블 업데이트
            reservationMapper.cancelReservation(
                    reservationId,
                    cancelDto.getCancelReason()
            );

            // 멘토에게 알림 전송
            MentoringReservationResponseDto responseDto = reservationMapper.selectMentoringReservationInfoById(reservationId);
            String contents = responseDto.getMenteeName() + "님 \n" +
                    responseDto.getDate().toLocalDate() + "에 예약된 " +
                    responseDto.getMentorName() + "님과의 멘토링이 취소되었습니다. \n" +
                    "취소 사유: " + (cancelDto.getCancelReason());
            notificationMapper.insertCancelNotificationMentoringReservationToMentor(responseDto.getMenteeId(), reservationId, contents);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void hideReservationByMentee(Long reservationId) {
        try {
            reservationMapper.hideReservationByMentee(reservationId);
        } catch (RuntimeException e) {
            throw new RuntimeException("멘토링 예약 닫기 실패", e);
        }
    }

    @Override
    @Transactional
    public void hideReservationByMentor(Long reservationId) {
        try {
            reservationMapper.hideReservationByMentor(reservationId);
        } catch (RuntimeException e) {
            throw new RuntimeException("멘토링 예약 닫기 실패", e);
        }
    }
}
