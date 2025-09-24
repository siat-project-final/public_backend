package com.takoyakki.backend.domain.studyDiary.service;

import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryAISummaryRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryInsertRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryUpdateRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiaryAISummaryResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import jakarta.validation.Valid;

import java.util.List;
public interface StudyDiaryService {
    int insertStudyDiary(@Valid StudyDiaryInsertRequestDto requestDto);

    List<StudyDiarySelectResponseDto> selectStudyDiaryList(Long memberId);

    int updateStudyDiary(Long id, StudyDiaryUpdateRequestDto requestDto);

    StudyDiarySelectResponseDto selectStudyDiary(Long diaryId);

    StudyDiaryAISummaryResponseDto getAISummary(@Valid StudyDiaryAISummaryRequestDto requestDto);

    List<StudyDiarySelectResponseDto> getStudyDiariesByMemberId(Long memberId);

    StudyDiarySelectResponseDto getStudyDiaryById(Long diaryId);

    List<StudyDiarySelectPublicListResponseDto> selectStudyDiaryListPublic(String subject);

    int changeStudyDiaryLike(Long diaryId, boolean isLike);  // 좋아요 기능 처리 메서드
    void deleteStudyDiary(Long diaryId);
}
