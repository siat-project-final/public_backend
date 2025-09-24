package com.takoyakki.backend.domain.myPage.service;

import com.takoyakki.backend.domain.challenge.dto.response.ChallengeRankResponseDto;
import com.takoyakki.backend.domain.challenge.repository.DailyChallengeRankingsMapper;
import com.takoyakki.backend.domain.challenge.repository.ProblemSolvingMapper;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageProblemSelectResponseDto;
import com.takoyakki.backend.domain.mentoring.repository.MentoringMapper;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageMentoringsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryServiceImpl implements HistoryService{
    private final MentoringMapper mentoringMapper;

    @Override
    public List<MyPageMentoringsResponseDto> selectMentoringHistory(Long memberId) {
        try {
            return mentoringMapper.selectMentoringHistory(memberId);
        } catch (Exception e) {
            throw new RuntimeException("멘토링 조회 중 문제가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private final DailyChallengeRankingsMapper dailyChallengeRankingsMapper;
    private final ProblemSolvingMapper problemSolvingMapper;

    @Override
    public List<ChallengeRankResponseDto> selectChallengeHistory(Long memberId) {
        try {
            return dailyChallengeRankingsMapper.selectChallengeRanksByMemberId(memberId);
        } catch (Exception e) {
            throw new RuntimeException("랭킹 조회 중 문제가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<MyPageProblemSelectResponseDto> selectChallengeDetail(Long memberId, LocalDate date) {
        try {
            return problemSolvingMapper.selectChallengeDetail(memberId, date);
        } catch (Exception e) {
            throw new RuntimeException("풀이 상세 조회 중 문제가 발생했습니다: " + e.getMessage(), e);
        }
    }
}
