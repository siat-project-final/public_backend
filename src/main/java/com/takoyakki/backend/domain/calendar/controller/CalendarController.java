//package com.takoyakki.backend.domain.calendar.controller;
//
//import com.takoyakki.backend.domain.calendar.service.CalendarService;
//import com.takoyakki.backend.domain.challenge.dto.response.ProblemsSelectResponseDto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.YearMonth;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/calendar")
//@Tag(name = "캘린더", description = "랜딩 캘린더 데이터 관리 API")
//public class CalendarController {
//    private final CalendarService calendarService;
//
//    @Operation(
//            summary = "멤버의 일정 전체 조회",
//            description = "멤버의 일정 전체를 조회합니다"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "멤버의 일정 전체 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
//            @ApiResponse(responseCode = "500", description = "서버 오류")
//    })
//    @GetMapping("/schedule/{memberId}/{month}")
//    public ResponseEntity<?> selectScheduleAllByMonth(@PathVariable("memberId") Long memberId, @PathVariable("month") String month ){
//        YearMonth yearMonth = YearMonth.parse(month);
//        return ResponseEntity.ok(calendarService.selectScheduleAllByMonth(memberId, yearMonth));
//    }
//
//}

package com.takoyakki.backend.domain.calendar.controller;

import com.takoyakki.backend.domain.calendar.dto.request.CalendarRequestDto;
import com.takoyakki.backend.domain.calendar.dto.response.CalendarResponseDto;
import com.takoyakki.backend.domain.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/calendar")
@Tag(name = "캘린더", description = "랜딩 캘린더 데이터 관리 API")
public class CalendarController {

    private final CalendarService calendarService;

    @Operation(summary = "멤버의 일정 전체 조회", description = "멤버의 월별 통합 일정을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/schedule/{memberId}/{month}")
    public ResponseEntity<?> selectScheduleAllByMonth(@PathVariable("memberId") Long memberId,
                                                      @PathVariable("month") String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        return ResponseEntity.ok(calendarService.selectScheduleAllByMonth(memberId, yearMonth));
    }

    // 일정 목록 조회
    @Operation(summary = "일정 목록 조회", description = "시작일과 종료일 사이의 일정을 조회합니다.")
    @GetMapping("/schedule/list/{memberId}")
    public ResponseEntity<List<CalendarResponseDto>> getSchedules(@PathVariable Long memberId,
                                                                  @RequestParam String startDate,
                                                                  @RequestParam String endDate) {
        return ResponseEntity.ok(calendarService.getSchedules(memberId, startDate, endDate));
    }

    // 일정 상세 조회
    @Operation(summary = "일정 상세 조회", description = "일정 ID로 상세 일정을 조회합니다.")
    @GetMapping("/schedule/detail/{scheduleId}")
    public ResponseEntity<CalendarResponseDto> getScheduleDetail(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(calendarService.getScheduleDetail(scheduleId));
    }

    // 일정 추가
    @Operation(summary = "일정 등록", description = "새로운 일정을 등록합니다.")
    @PostMapping("/schedule")
    public ResponseEntity<Void> addSchedule(@RequestBody CalendarRequestDto dto) {
        calendarService.addSchedule(dto);
        return ResponseEntity.ok().build();
    }

    // 일정 수정
    @Operation(summary = "일정 수정", description = "기존 일정을 수정합니다.")
    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long scheduleId,
                                               @RequestBody CalendarRequestDto dto) {
        calendarService.updateSchedule(scheduleId, dto);
        return ResponseEntity.ok().build();
    }

    // ✅ 일정 삭제
    @Operation(summary = "일정 삭제", description = "일정을 삭제합니다.")
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        calendarService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
