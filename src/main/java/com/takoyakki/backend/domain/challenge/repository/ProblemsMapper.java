package com.takoyakki.backend.domain.challenge.repository;

import com.takoyakki.backend.domain.challenge.dto.request.ProblemsInsertRequestDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemSolvingResultResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemSolvingSubmitResponseDto;
import com.takoyakki.backend.domain.challenge.dto.response.ProblemsSelectResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemsMapper {
    int insertProblem(ProblemsInsertRequestDto requestDto);

    ProblemSolvingSubmitResponseDto selectProblem(Long problemId);

    List<ProblemsSelectResponseDto> selectChallengeProblems();

    List<ProblemSolvingResultResponseDto> selectProblemSolvingResult(Long memberId);
}
