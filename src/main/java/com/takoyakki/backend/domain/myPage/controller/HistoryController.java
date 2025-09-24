package com.takoyakki.backend.domain.myPage.controller;

import com.takoyakki.backend.domain.myPage.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/myPage/history")
@Tag(name = "히스토리", description = "마이페이지 > 챌린지/멘토링 등의 히스토리 관련 API")
public class HistoryController {
    private final HistoryService historyService;

    @Operation(
            summary = "챌린지 히스토리 조회",
            description = "내 챌린지 랭킹 기록을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 랭킹 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/challenge/{memberId}")
    public ResponseEntity<?> selectChallengeRank(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(historyService.selectChallengeHistory(memberId));
    }

    @Operation(
            summary = "챌린지 히스토리 상세 조회",
            description = "챌린지 히스토리 클릭시 상세 내용을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 랭킹 단건 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/challenge/detail/{memberId}/{date}")
    public ResponseEntity<?> selectChallengeDetail(@PathVariable("memberId") Long memberId, @PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(historyService.selectChallengeDetail(memberId, date));
    }

    @Operation(
            summary = "멘토링 히스토리 조회",
            description = "내 멘토링 랭킹 기록을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘토링 랭킹 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/mentoring/{memberId}")
    public ResponseEntity<?> selectMentoringHistory(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(historyService.selectMentoringHistory(memberId));
    }
}
