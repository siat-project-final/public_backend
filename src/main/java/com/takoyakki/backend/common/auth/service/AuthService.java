package com.takoyakki.backend.common.auth.service;

import com.takoyakki.backend.common.auth.dto.LoginRequestDto;
import com.takoyakki.backend.common.auth.dto.LoginResponseDto;
import com.takoyakki.backend.common.auth.dto.SignUpAuthCheckDto;
import com.takoyakki.backend.common.auth.dto.SignUpRequestDto;
import jakarta.validation.Valid;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

    void logout(String accountId);

    int signUp(SignUpRequestDto requestDto);

    int checkStudentList(SignUpRequestDto requestDto);

    String reissueAccessToken(String refreshToken);
}
