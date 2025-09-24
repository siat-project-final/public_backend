package com.takoyakki.backend.common.auth.mapper;

import com.takoyakki.backend.common.auth.dto.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    LoginAuthCheckDto selectUserInfo(String id);

    int signUp(SignUpRequestDto requestDto);

    int selectStudentInfo(String memberName, String phoneNumber);

    SignUpDuplicationCheckDto checkSignUpDuplication(String memberName, String phoneNumber);
}

