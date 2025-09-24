package com.takoyakki.backend.domain.challenge.repository;

import com.takoyakki.backend.domain.challenge.dto.response.ChallengeRankResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyChallengeRankingsMapper {
    int insertDailyChallengeRanking(List<ChallengeRankResponseDto> list);

    List<ChallengeRankResponseDto> selectChallengeRanksByMemberId(Long memberId);

    int selectChallengeCount(Long memberId);
}
