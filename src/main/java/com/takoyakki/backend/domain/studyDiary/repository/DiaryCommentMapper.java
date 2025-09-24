package com.takoyakki.backend.domain.studyDiary.repository;

import com.takoyakki.backend.domain.studyDiary.dto.request.DiaryCommentsDeleteRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.DiaryCommentsInsertRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.DiaryCommentsUpdateRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.DiaryCommentsSelectResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryCommentMapper {

    List<DiaryCommentsSelectResponseDto> selectDiaryComments(Long diaryId);

    int insertDiaryComment(DiaryCommentsInsertRequestDto requestDto);
    void updateDiaryComment(@Param("commentId") Long commentId, @Param("dto") DiaryCommentsUpdateRequestDto dto);
    void deleteDiaryComment(@Param("commentId") Long commentId, @Param("dto") DiaryCommentsDeleteRequestDto dto);

}
