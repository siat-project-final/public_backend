package com.takoyakki.backend.domain.challenge.repository;

import com.takoyakki.backend.domain.challenge.dto.request.ProblemSolvingInsertItemRequestDto;
import com.takoyakki.backend.domain.challenge.dto.response.ChallengeRankResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemSolvingResultResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemsSelectResponseDto;
import com.takoyakki.backend.domain.myPage.dto.response.MyPageProblemSelectResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProblemSolvingMapper {

    // 문제 풀이 기록 삽입
    int insertProblemSolving(List<ProblemSolvingInsertItemRequestDto> requestDto);

    // 특정 날짜 기준 챌린지 랭킹 계산
    List<ChallengeRankResponseDto> calculateChallengeRank(LocalDate date);

    // 마이페이지 상세 풀이 내역 조회
    List<MyPageProblemSelectResponseDto> selectChallengeDetail(
            @Param("memberId") Long memberId,
            @Param("date") LocalDate date
    );

    // 챌린지 복습용 문제 조회
    List<ProblemsSelectResponseDto> selectChallengeReviewProblem(
            @Param("memberId") Long memberId,
            @Param("subject") String subject
    );

    // 특정 날짜에 제출한 기록 수 조회
    int countSubmissionsByMemberAndDate(
            @Param("memberId") Long memberId,
            @Param("date") LocalDate date
    );

    // 특정 날짜에 푼 문제의 채점 결과 조회
    List<ProblemSolvingResultResponseDto> selectScoringDetailByMemberAndDate(
            @Param("memberId") Long memberId,
            @Param("date") LocalDate date
    );

    // 사용자의 특정 문제 정답 개수 조회
    int countCorrectByMemberAndProblems(
            @Param("memberId") Long memberId,
            @Param("problemIds") List<Long> problemIds
    );

    // 누적 획득 점수 합계 조회
    Integer sumPointsByMember(
            @Param("memberId") Long memberId
    );
}
