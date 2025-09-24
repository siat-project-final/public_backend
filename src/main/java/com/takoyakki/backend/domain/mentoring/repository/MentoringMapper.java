package com.takoyakki.backend.domain.mentoring.repository;

import com.takoyakki.backend.domain.calendar.dto.response.CalendarItemMentoringByDateDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringCompleteRequestDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringResponseDto;

import com.takoyakki.backend.domain.myPage.dto.response.MyPageMentoringsResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MentoringMapper {

    // 멘토링 완료 처리 (멘토가 수행)
    void completeMentoring(MentoringCompleteRequestDto completeRequestDto);

    // 멘토링 상태 수동 업데이트 (예: 강제완료, 취소 등)
    void updateMentoringStatus(@Param("reservationId") Long reservationId,
                               @Param("status") String status);

    // 예약 ID 기준 오픈채팅 URL 조회
    String selectOpenChatUrlByReservationId(@Param("reservationId") Long reservationId);

    // 멘토링 단건 조회
    MentoringResponseDto selectMentoringResponseById(@Param("id") Long id);

    // 멘토 기준 완료된 멘토링 목록
    List<MentoringResponseDto> selectCompletedMentoringsByMentorId(@Param("mentorId") Long mentorId);

    // 멘티 기준 완료된 멘토링 목록
    List<MentoringResponseDto> selectCompletedMentoringsByMenteeId(@Param("menteeId") Long menteeId);

    List<MyPageMentoringsResponseDto> selectMentoringHistory(Long memberId);

    int selectMentoringCount(Long memberId);

    List<CalendarItemMentoringByDateDto> selectMentoringListInMonthByMemberId(Long memberId, LocalDate startDate, LocalDate endDate);

    int selectMentorIdByMemberId(Long mentorMemberId);
}
