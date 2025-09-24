package com.takoyakki.backend.domain.myPage.repository;

import com.takoyakki.backend.domain.myPage.dto.MemberSelectResponseDto;
import com.takoyakki.backend.domain.myPage.dto.MemberUpdateRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberSelectResponseDto selectMemberInfo(Long id);

    List<MemberSelectResponseDto> selectMemberInfoByAccountId(String accountId);

    int updateMemberInfo(MemberUpdateRequestDto updateDto);

    int getPointsByDailyChallengeRank(Long memberId, int points);

    void getPointsByStudyLog(Long memberId, int points);
}
