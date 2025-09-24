package com.takoyakki.backend.domain.notification.controller;

import com.takoyakki.backend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notifications")
@Tag(name = "알림 조회", description = "모든 알림 목록을 조회합니다")
public class NotificationController {
    private final NotificationService notificationService;

    // 멘토
    @Operation(
            summary = "멘토 알림 조회",
            description = "멘토에게 온 모든 알림을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘토 알림 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/mentor/{memberId}")
    public ResponseEntity<?> selectNotificationToMentor(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(notificationService.selectNotificationToMentor(memberId));
    }

    // 멘티
    @Operation(
            summary = "멘티 알림 조회",
            description = "멘티에게 온 모든 알림을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘티 알림 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/mentee/{memberId}")
    public ResponseEntity<?> selectNotificationToMentee(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(notificationService.selectNotificationToMentee(memberId));
    }

    @Operation(summary = "알림 닫기", description = "닫기를 누른 알림을 목록에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 삭제됨", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Map<String, Boolean>> softDeleteTodo(
            @Parameter(description = "알림 ID") @PathVariable Long notificationId) {
        boolean success = notificationService.softDeleteNotification(notificationId);
        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(Map.of("success", success));
    }
}
