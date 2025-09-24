package com.takoyakki.backend.domain.mentoring.service;

import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringCompleteRequestDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringResponseDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentorSimpleResponseDto;
import com.takoyakki.backend.domain.mentoring.repository.MentoringMapper;
import com.takoyakki.backend.domain.mentoring.repository.MentoringReservationMapper;
import com.takoyakki.backend.domain.mentoring.repository.MentorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final MentoringMapper mentoringMapper;
    private final MentoringReservationMapper mentoringReservationMapper;
    private final MentorMapper mentorMapper;

    @Override
    @Transactional
    public void completeMentoring(Long reservationId, MentoringCompleteRequestDto requestDto) {
        requestDto.setMentoringReservationId(reservationId);

        int mentorId = mentoringMapper.selectMentorIdByMemberId(requestDto.getMentorMemberId());
        requestDto.setMentorId((long) mentorId);
        mentoringMapper.completeMentoring(requestDto);
        mentoringReservationMapper.updateReservationToCompleted(reservationId);
    }

    @Override
    @Transactional
    public void updateMentoringStatus(Long reservationId, String status) {
        mentoringMapper.updateMentoringStatus(reservationId, status);
    }

    @Override
    public MentoringResponseDto getMentoringById(Long id) {
        return mentoringMapper.selectMentoringResponseById(id);
    }

    @Override
    public List<MentoringResponseDto> getMentoringListByMenteeId(Long menteeId) {
        return mentoringMapper.selectCompletedMentoringsByMenteeId(menteeId);
    }

    @Override
    public List<MentoringResponseDto> getMentoringListByMentorId(Long mentorId) {
        return mentoringMapper.selectCompletedMentoringsByMentorId(mentorId);
    }

    @Override
    public String getOpenChatUrlByReservationId(Long reservationId) {
        return mentoringMapper.selectOpenChatUrlByReservationId(reservationId);
    }

    // ✅ 새로 추가: 전체 멘토 목록 조회
    @Override
    public List<MentorSimpleResponseDto> getAllMentors() {
        return mentorMapper.findAllMentors();
    }
}
