package com.takoyakki.backend.domain.challenge.scheduler;

import com.takoyakki.backend.common.exception.ResourceNotFoundException;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeRankResponseDto;
import com.takoyakki.backend.domain.challenge.service.ChallengeService;
import com.takoyakki.backend.domain.dailyLearning.repository.DailyLearningMapper;
import com.takoyakki.backend.domain.myPage.repository.MemberMapper;
import com.takoyakki.backend.domain.notification.dto.NotificationChallengeToMenteeDto;
import com.takoyakki.backend.domain.notification.repository.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChallengeScheduler {
    private final DailyLearningMapper dailyLearningMapper;
    private final NotificationMapper notificationMapper;
    private final ChallengeService challengeService;

    @Transactional
    @Scheduled(cron = "0 0 7 * * *") // 매일 오전 7시
    public void createDailyChallengeProblems() {
        log.info("챌린지 문제 생성 스케줄러 실행 시작: {}", LocalDateTime.now());

        // 챌린지 문제 생성 로직
        try {
            List<String> subjects = null;
            LocalDate searchDate = LocalDate.now();
            int maxDays = 300;

            for (int i = 0; i < maxDays; i++) {
                subjects = dailyLearningMapper.selectDailyLearning(searchDate);
                if (subjects != null && !subjects.isEmpty()) {
                    log.info("해당 날짜에 등록된 학습 주제가 존재합니다.`: {}", searchDate);
                    break;
                }
                searchDate = searchDate.minusDays(1);
            }

            if (subjects == null || subjects.isEmpty()) {
                throw new ResourceNotFoundException("최근 " + maxDays + "일 내에 학습 데이터가 존재하지 않습니다.");
            }

            // 난이도 1~5 문제 생성
            for (int i = 1; i <= 5; i++) {
                challengeService.insertChallengeProblem(subjects.get(0), i);
            }

        } catch (Exception e) {
            log.error("챌린지 문제 생성 중 오류 발생: {}", e.getMessage(), e);
        }

        log.info("챌린지 문제 생성 스케줄러 실행 종료: {}", LocalDateTime.now());
    }


    @Transactional
    @Scheduled(cron = "0 59 23 * * *") // 매일 오후 11시 59분
    public void createDailyChallengeRanking() {
        log.info("챌린지 랭킹 생성 스케줄러 실행 시작: {}", LocalDateTime.now());

        // 챌린지 랭킹 생성 로직
        try {
            LocalDate today = LocalDate.now();
            List<ChallengeRankResponseDto> challengeRankResponseDtos = challengeService.selectChallengeRankByDate(today);

            if (challengeRankResponseDtos.isEmpty()) {
                log.info("오늘의 챌린지 랭킹 데이터가 없습니다: {}", today);
                throw new ResourceNotFoundException("오늘의 챌린지 랭킹 데이터가 없습니다");
            } else {
                log.info("오늘의 챌린지 랭킹 데이터 생성 완료: {}", today);
            }

            // 데일리 랭킹 기록
            challengeService.insertDailyChallengeRanking(challengeRankResponseDtos);

            // 데일리 랭킹 마감시 1~3등에게 포인트 지급
            for (ChallengeRankResponseDto dto : challengeRankResponseDtos) {
                int rank = dto.getRank();
                Long memberId = dto.getMemberId();
                challengeService.getPointsByDailyChallengeRank(memberId, rank);

                if (rank <= 3) {
                    // 포인트 지급 알림
                    String message = String.format("데일리 챌린지 결과 %d등 축하드립니다! 포인트를 지급해드렸습니다.", rank);

                    NotificationChallengeToMenteeDto pointsDto = NotificationChallengeToMenteeDto.builder()
                            .memberId(memberId)
                            .title("챌린지 포인트 지급 알림")
                            .contents(message)
                            .build();
                    notificationMapper.insertNotificationChallengeRankPointsToMentee(pointsDto);
                }
            }

        } catch (Exception e) {
            log.error("챌린지 랭킹 생성 중 오류 발생: {}", e.getMessage(), e);
        }

        log.info("챌린지 랭킹 생성 스케줄러 실행 종료: {}", LocalDateTime.now());

    }
}