package com.takoyakki.backend.domain.challenge.service;

import com.takoyakki.backend.domain.challenge.api.AnthropicClient;
import com.takoyakki.backend.domain.challenge.dto.request.ProblemSolvingInsertItemRequestDto;
import com.takoyakki.backend.domain.challenge.dto.request.ProblemSolvingInsertRequestDto;
import com.takoyakki.backend.domain.challenge.dto.request.ProblemsInsertRequestDto;
import com.takoyakki.backend.domain.challenge.dto.response.*;
import com.takoyakki.backend.domain.challenge.repository.DailyChallengeRankingsMapper;
import com.takoyakki.backend.domain.challenge.repository.ProblemSolvingMapper;
import com.takoyakki.backend.domain.challenge.repository.ProblemsMapper;
import com.takoyakki.backend.domain.dailyLearning.repository.DailyLearningMapper;
import com.takoyakki.backend.domain.myPage.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    private final ProblemsMapper problemsMapper;
    private final ProblemSolvingMapper problemSolvingMapper;
    private final DailyChallengeRankingsMapper dailyChallengeRankingsMapper;
    private final DailyLearningMapper dailyLearningMapper;
    private final MemberMapper memberMapper;
    private final AnthropicClient anthropicClient;

    @Override
    public List<ProblemsSelectResponseDto> selectChallengeProblems() {
        try {
            return problemsMapper.selectChallengeProblems();
        } catch (Exception e) {
            throw new RuntimeException("문제 조회 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProblemSolvingResultResponseDto> selectProblemSolvingResult(Long memberId) {
        return problemsMapper.selectProblemSolvingResult(memberId);
    }

    @Override
    @Transactional
    public int insertChallengeProblem(String subject, int difficulty) {
        try {
            String problem  = anthropicClient.createProblem(subject, difficulty);
            String contents = anthropicClient.extractContents(problem);
            int answer      = anthropicClient.extractAnswer(problem);
            String title    = anthropicClient.extractTitle(contents);
            List<String> choices = anthropicClient.extractChoice(contents);

            ProblemsInsertRequestDto dto = ProblemsInsertRequestDto.builder()
                    .title(title)
                    .contents(contents)
                    .difficulty(difficulty)
                    .subject(subject)
                    .correctAnswer(answer)
                    .choices(choices)
                    .build();

            return problemsMapper.insertProblem(dto);
        } catch (Exception e) {
            throw new RuntimeException("문제 생성 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int insertProblemSolving(ProblemSolvingInsertRequestDto requestDto) {
        try {
            List<Long> problemIds = requestDto.getProblemIds();
            List<Integer> answers = requestDto.getAnswers();

            List<ProblemSolvingInsertItemRequestDto> list = new ArrayList<>();
            for (int i = 0; i < problemIds.size(); i++) {
                Long problemId = problemIds.get(i);
                Integer answer = answers.get(i);

                ProblemSolvingSubmitResponseDto resp = problemsMapper.selectProblem(problemId);

                ProblemSolvingInsertItemRequestDto item = ProblemSolvingInsertItemRequestDto.builder()
                        .memberId(requestDto.getMemberId())
                        .problemId(problemId)
                        .createdAt(requestDto.getCreatedAt())
                        .answer(answer)
                        .isCorrect(answer.equals(resp.getAnswer()) )
                        .points(answer.equals(resp.getAnswer()) ? resp.getDifficulty() : 0)
                        .build();
                list.add(item);
            }

            return problemSolvingMapper.insertProblemSolving(list);
        } catch (Exception e) {
            throw new RuntimeException("문제 풀이 제출 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ChallengeRankResponseDto> selectChallengeRankByDate(LocalDate date) {
        try {
            return problemSolvingMapper.calculateChallengeRank(date);
        } catch (Exception e) {
            throw new RuntimeException("랭킹 조회 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int insertDailyChallengeRanking(List<ChallengeRankResponseDto> dtos) {
        try {
            return dailyChallengeRankingsMapper.insertDailyChallengeRanking(dtos);
        } catch (Exception e) {
            throw new RuntimeException("랭킹 삽입 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int getPointsByDailyChallengeRank(Long memberId, int rank) {
        int bonus;
        switch (rank) {
            case 1: bonus = 30; break;
            case 2: bonus = 20; break;
            case 3: bonus = 10; break;
            default: bonus = 0;
        }
        log.info("포인트 지급 - memberId: {}, rank: {}, bonus: {}", memberId, rank, bonus);
        return memberMapper.getPointsByDailyChallengeRank(memberId, bonus);
    }

    @Override
    public List<ChallengeReviewSelectResponseDto> selectChallengeReviewList() {
        try {
            return dailyLearningMapper.selectDailyLearningProgress();
        } catch (Exception e) {
            throw new RuntimeException("복습 리스트 조회 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    public ProblemsSelectResponseDto selectChallengeReviewProblem(Long memberId, String subject) {
        try {
            List<ProblemsSelectResponseDto> list =
                    problemSolvingMapper.selectChallengeReviewProblem(memberId, subject);
            int idx = ThreadLocalRandom.current().nextInt(list.size());
            return list.get(idx);
        } catch (Exception e) {
            throw new RuntimeException("복습 문제 조회 중 오류: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean checkParticipation(Long memberId, LocalDate date) {
        return problemSolvingMapper.countSubmissionsByMemberAndDate(memberId, date) > 0;
    }

    @Override
    public List<ProblemSolvingResultResponseDto> getScoringResult(Long memberId, LocalDate date) {
        return problemSolvingMapper.selectScoringDetailByMemberAndDate(memberId, date);
    }

    @Override
    public int calculateXp(Long memberId, List<Long> problemIds) {
        int correctCount = problemSolvingMapper.countCorrectByMemberAndProblems(memberId, problemIds);
        return correctCount * 10;
    }

    @Override
    public int getTotalPoints(Long memberId) {
        Integer sum = problemSolvingMapper.sumPointsByMember(memberId);
        return sum != null ? sum : 0;
    }
}
