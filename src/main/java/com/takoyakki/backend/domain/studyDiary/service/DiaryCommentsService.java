package com.takoyakki.backend.domain.studyDiary.service;

import com.takoyakki.backend.domain.studyDiary.dto.request.*;
import com.takoyakki.backend.domain.studyDiary.dto.response.DiaryCommentsSelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiaryAISummaryResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface DiaryCommentsService {

    List<DiaryCommentsSelectResponseDto> selectDiaryComments(Long diaryId);

    int insertDiaryComment(@Valid DiaryCommentsInsertRequestDto requestDto);
    void updateComment(Long commentId, DiaryCommentsUpdateRequestDto dto);
    void deleteComment(Long commentId, DiaryCommentsDeleteRequestDto dto);

}
