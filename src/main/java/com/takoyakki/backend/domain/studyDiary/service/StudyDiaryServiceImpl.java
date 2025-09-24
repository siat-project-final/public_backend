package com.takoyakki.backend.domain.studyDiary.service;

import com.takoyakki.backend.domain.myPage.repository.MemberMapper;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryAISummaryRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryInsertRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.request.StudyDiaryUpdateRequestDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiaryAISummaryResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectPublicListResponseDto;
import com.takoyakki.backend.domain.studyDiary.dto.response.StudyDiarySelectResponseDto;
import com.takoyakki.backend.domain.studyDiary.api.SummaryAnthropicClient;
import com.takoyakki.backend.domain.studyDiary.repository.StudyDiraryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyDiaryServiceImpl implements StudyDiaryService {

    private final StudyDiraryMapper studyDiraryMapper;
    private final SummaryAnthropicClient summaryAnthropicClient;
    private final MemberMapper  memberMapper;

    @Override
    @Transactional
    public int insertStudyDiary(StudyDiaryInsertRequestDto requestDto) {
        try {

            // 1 학습일지 insert
            int retVal = studyDiraryMapper.insertStudyDiary(requestDto);

            // 2 학습일지 작성 성공시 포인트 지급
            memberMapper.getPointsByStudyLog(1L, 10);

            return retVal;
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 작성 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudyDiarySelectResponseDto> selectStudyDiaryList(Long memberId) {
        try {
            return studyDiraryMapper.selectStudyDiaryList(memberId);
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 전체 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public StudyDiarySelectResponseDto selectStudyDiary(Long diaryId) {
        try {
            StudyDiarySelectResponseDto result = studyDiraryMapper.selectStudyDiary(diaryId);
            if (result == null) {
                throw new RuntimeException("해당 학습 일지를 찾을 수 없습니다. diaryId=" + diaryId);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int updateStudyDiary(Long id, StudyDiaryUpdateRequestDto requestDto) {
        try {
            return studyDiraryMapper.updateStudyDiary(id, requestDto);
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 수정 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public StudyDiarySelectResponseDto getStudyDiaryById(Long diaryId) {
        try {
            StudyDiarySelectResponseDto result = studyDiraryMapper.selectStudyDiaryById(diaryId);
            if (result == null) {
                throw new RuntimeException("해당 일지를 찾을 수 없습니다. diaryId=" + diaryId);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudyDiarySelectPublicListResponseDto> selectStudyDiaryListPublic(String subject) {
        try {
            return studyDiraryMapper.selectStudyDiaryListPublic(subject);
        } catch (Exception e) {
            throw new RuntimeException("공개 학습 일지 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public int changeStudyDiaryLike(Long diaryId, boolean isLike) {
        try {
            return studyDiraryMapper.changeStudyDiaryLike(diaryId, isLike);
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 좋아요 처리 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public StudyDiaryAISummaryResponseDto getAISummary(StudyDiaryAISummaryRequestDto requestDto) {
        try {
            return StudyDiaryAISummaryResponseDto.builder()
                    .result(summaryAnthropicClient.createSummary(requestDto.getText()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 AI 요약 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudyDiarySelectResponseDto> getStudyDiariesByMemberId(Long memberId) {
        try {
            return studyDiraryMapper.selectStudyDiariesByMemberId(memberId);
        } catch (Exception e) {
            throw new RuntimeException("사용자 학습 일지 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    // ✅ 삭제 기능 추가
    @Override
    @Transactional
    public void deleteStudyDiary(Long diaryId) {
        try {
            int result = studyDiraryMapper.deleteDiary(diaryId);
            if (result == 0) {
                throw new RuntimeException("삭제할 학습 일지를 찾을 수 없습니다. diaryId=" + diaryId);
            }
        } catch (Exception e) {
            throw new RuntimeException("학습 일지 삭제 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
}
