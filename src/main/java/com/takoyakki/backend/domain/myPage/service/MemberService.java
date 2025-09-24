package com.takoyakki.backend.domain.myPage.service;

import com.takoyakki.backend.domain.myPage.dto.MemberSelectResponseDto;
import com.takoyakki.backend.domain.myPage.dto.MemberUpdateRequestDto;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageStatisticsResponseDto;

public interface MemberService {
    MemberSelectResponseDto selectMemberInfo(Long memberId);

    int updateMemberInfo(Long memberId, MemberUpdateRequestDto updateDto);

    MyPageStatisticsResponseDto getStatistics(Long memberId);
}
