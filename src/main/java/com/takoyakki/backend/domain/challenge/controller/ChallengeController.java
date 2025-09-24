package com.takoyakki.backend.domain.challenge.controller;

import com.takoyakki.backend.domain.challenge.dto.request.ProblemSolvingInsertRequestDto;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeReviewSelectResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeResultResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemSolvingResultResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemsSelectResponseDto;
import com.takoyakki.backend.domain.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/challenge")
@Tag(name = "AI 문제 챌린지", description = "AI 문제 챌린지 관련 API")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "오늘의 챌린지 문제 조회", description = "오늘의 챌린지 문제를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 문제 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<List<ProblemsSelectResponseDto>> selectChallengeProblems() {
        return ResponseEntity.ok(challengeService.selectChallengeProblems());
    }

    @Operation(summary = "문제 풀이 제출", description = "문제를 풀이하고 제출합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "문제 풀이 제출 성공"),
            @ApiResponse(responseCode = "400", description = "이미 제출한 기록이 있음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<ChallengeResultResponseDto> insertProblemSolving(
            @Valid @RequestBody ProblemSolvingInsertRequestDto requestDto) {

        boolean alreadySubmitted = challengeService.checkParticipation(
                requestDto.getMemberId(),
                requestDto.getCreatedAt().toLocalDate()
        );

        if (alreadySubmitted) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ChallengeResultResponseDto.builder()
                            .xp(0)
                            .point(challengeService.getTotalPoints(requestDto.getMemberId()))
                            .build());
        }

        // 1) 문제 풀이 저장
        challengeService.insertProblemSolving(requestDto);

        // 2) 이번 제출로 획득한 XP 계산
        int xp = challengeService.calculateXp(
                requestDto.getMemberId(),
                requestDto.getProblemIds()
        );

        // 3) 누적 포인트 조회
        int point = challengeService.getTotalPoints(requestDto.getMemberId());

        ChallengeResultResponseDto result = ChallengeResultResponseDto.builder()
                .xp(xp)
                .point(point)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Operation(summary = "문제 채점 결과 조회", description = "사용자의 채점 결과를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "204", description = "결과 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{memberId}/scoring")
    public ResponseEntity<List<ProblemSolvingResultResponseDto>> getSubmissionResult(
            @PathVariable Long memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date
    ) {
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(challengeService.getScoringResult(memberId, targetDate));
    }

    @Operation(summary = "챌린지 랭킹 조회", description = "지정된 날짜의 챌린지 랭킹을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "랭킹 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/rank")
    public ResponseEntity<?> selectChallengeRankByDate(
            @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(challengeService.selectChallengeRankByDate(date));
    }

    @Operation(summary = "챌린지 복습 리스트 조회", description = "챌린지 복습 과목 리스트를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복습 리스트 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/review")
    public ResponseEntity<List<ChallengeReviewSelectResponseDto>> selectChallengeReviewList() {
        return ResponseEntity.ok(challengeService.selectChallengeReviewList());
    }

    @Operation(summary = "챌린지 복습 문제 조회", description = "과목 선택 시 복습 문제를 랜덤으로 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복습 문제 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/review/{memberId}/{subject}")
    public ResponseEntity<ProblemsSelectResponseDto> selectChallengeReviewProblem(
            @PathVariable("memberId") Long memberId,
            @PathVariable("subject") String subject) {
        return ResponseEntity.ok(challengeService.selectChallengeReviewProblem(memberId, subject));
    }

    @Operation(summary = "챌린지 참여 여부 확인", description = "특정 날짜에 사용자가 챌린지에 참여했는지 여부를 반환합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/participation")
    public ResponseEntity<Map<String, Boolean>> checkParticipation(
            @Parameter(description = "사용자 ID", example = "1")
            @RequestParam("memberId") Long memberId,

            @Parameter(description = "조회 날짜 (yyyy-MM-dd)", example = "2025-06-30")
            @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date
    ) {
        boolean participated = challengeService.checkParticipation(memberId, date);
        return ResponseEntity.ok(Map.of("participated", participated));
    }
}
