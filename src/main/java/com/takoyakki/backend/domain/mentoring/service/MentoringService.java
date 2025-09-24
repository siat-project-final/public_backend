package com.takoyakki.backend.domain.mentoring.service;

import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentorSimpleResponseDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringCompleteRequestDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringResponseDto;

import java.util.List;

public interface MentoringService {

    // 멘토링 완료 처리
    void completeMentoring(Long reservationId, MentoringCompleteRequestDto requestDto);

    // 멘토링 상태 수동 업데이트 (강제완료, 취소 등)
    void updateMentoringStatus(Long reservationId, String status);

    // 멘토링 단건 조회
    MentoringResponseDto getMentoringById(Long mentoringId);

    // 멘티 완료 멘토링 목록 조회
    List<MentoringResponseDto> getMentoringListByMenteeId(Long menteeId);

    // 멘토 완료 멘토링 목록 조회
    List<MentoringResponseDto> getMentoringListByMentorId(Long mentorId);

    // 오픈채팅 URL 조회
    String getOpenChatUrlByReservationId(Long reservationId);

    List<MentorSimpleResponseDto> getAllMentors();

}
