package com.takoyakki.backend.common.auth.service;

import com.takoyakki.backend.common.auth.mapper.MentorQueryMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorQueryService {
    @Qualifier("mentorQueryMapper")
    private final MentorQueryMapper mentorMapper;

    public Long getMentorIdByMemberId(Long memberId) {
        Long mentorId = mentorMapper.findMentorIdByMemberId(memberId);
        if (mentorId == null) {
            throw new IllegalArgumentException("해당 memberId로 등록된 멘토가 없습니다.");
        }
        return mentorId;
    }
}
