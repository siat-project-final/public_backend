package com.takoyakki.backend.common.auth.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MentorQueryMapper {
    Long findMentorIdByMemberId(Long memberId);
}
