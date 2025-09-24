package com.takoyakki.backend.domain.studyDiary.controller;

import com.takoyakki.backend.domain.studyDiary.dto.request.*;
import com.takoyakki.backend.domain.studyDiary.dto.response.DiaryCommentsSelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.service.DiaryCommentsService;
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
@RequestMapping("/v1/study-diary/comments")
@Tag(name = "학습 일지 댓글", description = "학습 일지 댓글 관련 API")
public class DiaryCommentsController {

    private final DiaryCommentsService diaryCommentsService;

    @Operation(summary = "특정 다이어리의 댓글 전체 조회", description = "특정 다이어리의 댓글 전체를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<List<DiaryCommentsSelectResponseDto>> selectDiaryComments(@PathVariable Long diaryId) {
        return ResponseEntity.ok(diaryCommentsService.selectDiaryComments(diaryId));
    }

    @Operation(summary = "학습 일지 댓글 작성", description = "학습 일지에 댓글을 작성합니다")
    @PostMapping
    public ResponseEntity<?> insertDiaryComment(@Valid @RequestBody DiaryCommentsInsertRequestDto requestDto) {
        diaryCommentsService.insertDiaryComment(requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학습 일지 댓글 수정", description = "학습 일지에 댓글을 수정합니다")
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody DiaryCommentsUpdateRequestDto requestDto) {
        diaryCommentsService.updateComment(commentId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학습 일지 댓글 삭제", description = "학습 일지에 댓글을 soft delete 합니다")
    @PutMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestBody DiaryCommentsDeleteRequestDto requestDto) {
        diaryCommentsService.deleteComment(commentId, requestDto);
        return ResponseEntity.ok().build();
    }
}
