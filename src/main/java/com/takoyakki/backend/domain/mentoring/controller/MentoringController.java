package com.takoyakki.backend.domain.mentoring.controller;

import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentorSimpleResponseDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringCompleteRequestDto;
import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentoringResponseDto;
import com.takoyakki.backend.domain.mentoring.service.MentoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/mentoring")
@RequiredArgsConstructor
@Tag(name = "멘토링 관리", description = "실제 멘토링 세션 이후의 관리 API")
public class MentoringController {

    private final MentoringService mentoringService;

    @Operation(summary = "멘토링 완료 처리 (멘토)")
    @PostMapping("/mentor/{reservationId}/complete")
    public void completeMentoring(@PathVariable Long reservationId,
                                  @RequestBody @Valid MentoringCompleteRequestDto requestDto) {
        mentoringService.completeMentoring(reservationId, requestDto);
    }

    @Operation(summary = "멘토링 단건 조회")
    @GetMapping("/{mentoringId}")
    public MentoringResponseDto getMentoringById(@PathVariable Long mentoringId) {
        return mentoringService.getMentoringById(mentoringId);
    }

    @Operation(summary = "멘토 기준 완료된 멘토링 목록 조회")
    @GetMapping("/mentor/{mentorId}/completed")
    public List<MentoringResponseDto> getCompletedMentoringsByMentor(@PathVariable Long mentorId) {
        return mentoringService.getMentoringListByMentorId(mentorId);
    }

    @Operation(summary = "멘티 기준 완료된 멘토링 목록 조회")
    @GetMapping("/mentee/{menteeId}/completed")
    public List<MentoringResponseDto> getCompletedMentoringsByMentee(@PathVariable Long menteeId) {
        return mentoringService.getMentoringListByMenteeId(menteeId);
    }

    @Operation(summary = "오픈채팅 URL 조회")
    @GetMapping("/mentee/{reservationId}/open-chat")
    public String getOpenChatUrl(@PathVariable Long reservationId) {
        return mentoringService.getOpenChatUrlByReservationId(reservationId);
    }

    @Operation(summary = "전체 멘토 목록 조회")
    @GetMapping("/mentors")
    public List<MentorSimpleResponseDto> getAllMentors() {
        return mentoringService.getAllMentors(); // 이 메서드를 서비스에 추가하면 됨
}

//    @Operation(summary = "멘토링 상태 수동 변경 (강제완료, 취소 등)")
//    @PatchMapping("/mentor/{reservationId}/status")
//    public void updateMentoringStatus(@PathVariable Long reservationId,
//                                      @RequestParam String status) {
//        mentoringService.updateMentoringStatus(reservationId, status);
//    }
}
