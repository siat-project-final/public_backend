package com.takoyakki.backend.domain.myPage.controller;

import com.takoyakki.backend.common.api.ApiResult;
import com.takoyakki.backend.domain.myPage.dto.MemberSelectResponseDto;
import com.takoyakki.backend.domain.myPage.dto.MemberUpdateRequestDto;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageStatisticsResponseDto;
import com.takoyakki.backend.domain.myPage.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/myPage/members")
@Tag(name = "회원 관리", description = "마이페이지 > 내 정보 조회, 수정, 탈퇴 등 회원관리 API")
public class MemberController {
    private final MemberService memberService;

    @Operation(
            summary = "내 정보 조회",
            description = "멤버 자신의 정보를 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberSelectResponseDto> createMember(@PathVariable("memberId") Long memberId){
        MemberSelectResponseDto memberSelectResponseDto = memberService.selectMemberInfo(memberId);
        Long selectedMemberId = memberSelectResponseDto.getMemberId();
        return ResponseEntity
                .created(URI.create("/v1/members/" + selectedMemberId))
                .body(memberSelectResponseDto);
    }

    @Operation(
            summary = "회원 정보 수정",
            description = "멤버 자신의 정보를 수정합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResult<String>> updateMember(
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody MemberUpdateRequestDto updateDto) {
        memberService.updateMemberInfo(memberId, updateDto);
        return ResponseEntity.ok(ApiResult.success("회원 정보 수정 성공"));
    }

    @Operation(
            summary = "통계 조회",
            description = "멤버 자신의 통계를 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{memberId}/stats")
    public ResponseEntity<MyPageStatisticsResponseDto> getStatistics(@PathVariable("memberId") Long memberId){
        MyPageStatisticsResponseDto myPageStatisticsResponseDto = memberService.getStatistics(memberId);
        return ResponseEntity.ok(myPageStatisticsResponseDto);
    }
}
