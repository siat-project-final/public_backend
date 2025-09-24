package com.takoyakki.backend.domain.studyDiary.controller;

import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryAISummaryRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryInsertRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryUpdateRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiaryAISummaryResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.service.StudyDiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/study-diary")
@Tag(name = "학습 일지", description = "학습 일지 관련 API")
public class StudyDiaryController {
    private final StudyDiaryService studyDiaryService;

    @Operation(summary = "학습 일지 AI 요약", description = "학습 일지 작성시 AI 요약 항목을 출력합니다")
    @ApiResponse(responseCode = "200", description = "학습 일지 AI 요약 출력 성공")
    @PostMapping("/ai-summary")
    public ResponseEntity<StudyDiaryAISummaryResponseDto> getAISummary(@Valid @RequestBody StudyDiaryAISummaryRequestDto requestDto) {
        return ResponseEntity.ok(studyDiaryService.getAISummary(requestDto));
    }

    @Operation(summary = "학습 일지 제출", description = "학습 일지를 제출합니다")
    @ApiResponse(responseCode = "201", description = "학습 일지 제출 성공")
    @PostMapping
    public ResponseEntity<?> insertStudyDiary(@Valid @RequestBody StudyDiaryInsertRequestDto requestDto) {
        studyDiaryService.insertStudyDiary(requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학습 일지 단건 조회", description = "특정 학습 일지를 조회합니다")
    @ApiResponse(responseCode = "200", description = "학습 일지 조회 성공")
    @GetMapping("/{diaryId}")
    public ResponseEntity<StudyDiarySelectResponseDto> selectStudyDiary(@PathVariable Long diaryId) {
        return ResponseEntity.ok(studyDiaryService.selectStudyDiary(diaryId));
    }

    @Operation(summary = "특정 회원의 학습 일지 목록 조회", description = "특정 회원의 학습 일지 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "학습 일지 목록 조회 성공")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<StudyDiarySelectResponseDto>> selectStudyDiaryList(@PathVariable Long memberId) {
        return ResponseEntity.ok(studyDiaryService.selectStudyDiaryList(memberId));
    }

    @Operation(summary = "학습 일지 수정", description = "학습 일지를 수정합니다")
    @ApiResponse(responseCode = "200", description = "학습 일지 수정 성공")
    @PutMapping("/{diaryId}")
    public ResponseEntity<?> updateStudyDiary(@PathVariable Long diaryId, @Valid @RequestBody StudyDiaryUpdateRequestDto requestDto) {
        studyDiaryService.updateStudyDiary(diaryId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학습 일지 삭제", description = "학습 일지를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "학습 일지 삭제 성공")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteStudyDiary(@PathVariable Long diaryId) {
        studyDiaryService.deleteStudyDiary(diaryId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "공개된 학습 일지 목록 전체 조회", description = "공개된 학습 일지 전체 목록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/public")
    public ResponseEntity<List<StudyDiarySelectPublicListResponseDto>> selectStudyDiaryListPublic(
            @RequestParam(required = false, defaultValue = "ALL") String subject) {
        return ResponseEntity.ok(studyDiaryService.selectStudyDiaryListPublic(subject));
    }

    @Operation(summary = "학습 일지 좋아요", description = "학습 일지에 좋아요를 늘리거나 차감합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/like/{diaryId}")
    public ResponseEntity<?> updateStudyDiaryLike(@PathVariable Long diaryId, @RequestParam boolean isLike) {
        return ResponseEntity.ok(studyDiaryService.changeStudyDiaryLike(diaryId, isLike));
    }

}