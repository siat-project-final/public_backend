package com.takoyakki.backend.domain.studyDiary.repository;

import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryInsertRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryUpdateRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyDiraryMapper {
    int insertStudyDiary(StudyDiaryInsertRequestDto requestDto);

    StudyDiarySelectResponseDto selectStudyDiary(Long diaryId);

    int updateStudyDiary(Long id, StudyDiaryUpdateRequestDto requestDto);

    List<StudyDiarySelectResponseDto> selectStudyDiaryList(Long memberId);

    // 전체 일지 조회 (특정 회원 기준)
    List<StudyDiarySelectResponseDto> selectStudyDiariesByMemberId(@Param("memberId") Long memberId);
    // 단건 조회
    StudyDiarySelectResponseDto selectStudyDiaryById(@Param("diaryId") Long diaryId);

    List<StudyDiarySelectPublicListResponseDto> selectStudyDiaryListPublic(String subject);
    int deleteDiary(@Param("diaryId") Long diaryId);
    int changeStudyDiaryLike(Long diaryId, boolean isLike);

    int selectStudyDiaryCount(Long memberId);

    //퍼블릭이랑 안퍼블랙.
}
