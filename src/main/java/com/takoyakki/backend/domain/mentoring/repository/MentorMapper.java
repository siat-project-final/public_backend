package com.takoyakki.backend.domain.mentoring.repository;

import com.takoyakki.backend.domain.mentoring.dto.mentoring.MentorSimpleResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MentorMapper {

    List<MentorSimpleResponseDto> findAllMentors();

    // ✅ 멘토 role 부여
    void promoteToMentor(@Param("memberId") Long memberId);

    // ✅ mentors 테이블에 insert
    void insertMentorFromMember(@Param("memberId") Long memberId);
}
