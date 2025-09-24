package com.takoyakki.backend.domain.studyDiary.service;

import com.takoyakki.backend.domain.studyDiary.api.SummaryAnthropicClient;
import com.takoyakki.backend.domain.studyDiary.dto.request.*;
import com.takoyakki.backend.domain.studyDiary.dto.response.DiaryCommentsSelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiaryAISummaryResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.repository.DiaryCommentMapper;
import com.takoyakki.backend.domain.studyDiary.repository.StudyDiraryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryCommentsServiceImpl implements DiaryCommentsService{
    private final DiaryCommentMapper diaryCommentMapper;


    @Override
    public List<DiaryCommentsSelectResponseDto> selectDiaryComments(Long diaryId) {
        try {
            return diaryCommentMapper.selectDiaryComments(diaryId);
        } catch (Exception e) {
            throw new RuntimeException("댓글 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int insertDiaryComment(DiaryCommentsInsertRequestDto requestDto) {
        try {
            return diaryCommentMapper.insertDiaryComment(requestDto);
        } catch (Exception e) {
            throw new RuntimeException("댓글 입력 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void updateComment(Long commentId, DiaryCommentsUpdateRequestDto dto) {
        diaryCommentMapper.updateDiaryComment(commentId, dto);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, DiaryCommentsDeleteRequestDto dto) {
        diaryCommentMapper.deleteDiaryComment(commentId, dto);
    }

}
