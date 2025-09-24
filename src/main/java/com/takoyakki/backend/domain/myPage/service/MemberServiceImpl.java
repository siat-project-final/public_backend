package com.takoyakki.backend.domain.myPage.service;

import com.takoyakki.backend.common.exception.ResourceNotFoundException;
import com.takoyakki.backend.common.exception.UnauthorizedException;
import com.takoyakki.backend.domain.challenge.repository.DailyChallengeRankingsMapper;
import com.takoyakki.backend.domain.mentoring.repository.MentoringMapper;
import com.takoyakki.backend.domain.myPage.dto.MemberSelectResponseDto;
import com.takoyakki.backend.domain.myPage.dto.MemberUpdateRequestDto;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageStatisticsResponseDto;
import com.takoyakki.backend.domain.myPage.repository.MemberMapper;
import com.takoyakki.backend.domain.studyDiary.repository.StudyDiraryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final DailyChallengeRankingsMapper dailyChallengeRankingsMapper;
    private final MentoringMapper mentoringMapper;
    private final StudyDiraryMapper studyDiraryMapper;

    @Override
    public MemberSelectResponseDto selectMemberInfo(Long memberId) {
        return Optional.ofNullable(memberMapper.selectMemberInfo(memberId))
                .orElseThrow(() -> new UnauthorizedException("회원 정보를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public int updateMemberInfo(Long memberId, MemberUpdateRequestDto updateDto) {
        updateDto.setMemberId(memberId);
        MemberSelectResponseDto memberSelectResponseDto = memberMapper.selectMemberInfo(memberId);
        if (memberSelectResponseDto == null) {
            throw new ResourceNotFoundException("존재하지 않는 멤버입니다");
        }

        return memberMapper.updateMemberInfo(updateDto);
    }

    @Override
    public MyPageStatisticsResponseDto getStatistics(Long memberId) {
        try {
            // 학습일지 추가 필요
            int studyDiaryCount = studyDiraryMapper.selectStudyDiaryCount(memberId);
            int challengeCount = dailyChallengeRankingsMapper.selectChallengeCount(memberId);
            int mentoringCount = mentoringMapper.selectMentoringCount(memberId);
            int totalXp = memberMapper.selectMemberInfo(memberId).getTotalXp();

            return MyPageStatisticsResponseDto.builder()
                    .studyDiaryCount(studyDiaryCount)
                    .challengeCount(challengeCount)
                    .mentoringCount(mentoringCount)
                    .totalXp(totalXp)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("통계 조회 중 문제가 발생했습니다: " + e.getMessage(), e);
        }
    }
}
