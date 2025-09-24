package com.takoyakki.backend.domain.challenge.service;

import com.takoyakki.backend.domain.challenge.dto.request.ProblemSolvingInsertRequestDto;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeRankResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeReviewSelectResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemSolvingResultResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemsSelectResponseDto;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeService {
    int insertChallengeProblem(String subject, int difficulty);

    int insertProblemSolving(@Valid ProblemSolvingInsertRequestDto requestDto);

    List<ChallengeRankResponseDto> selectChallengeRankByDate(LocalDate date);

    int insertDailyChallengeRanking(List<ChallengeRankResponseDto> challengeRankResponseDtos);

    List<ChallengeReviewSelectResponseDto> selectChallengeReviewList();

    ProblemsSelectResponseDto selectChallengeReviewProblem(Long memberId, String subject);

    List<ProblemsSelectResponseDto> selectChallengeProblems();

    List<ProblemSolvingResultResponseDto> selectProblemSolvingResult(Long memberId);

    int getPointsByDailyChallengeRank(Long memberId, int rank);

    boolean checkParticipation(Long memberId, LocalDate date);

    List<ProblemSolvingResultResponseDto> getScoringResult(Long memberId, LocalDate date);

    int calculateXp(Long memberId, List<Long> problemIds);
    int getTotalPoints(Long memberId);
}
