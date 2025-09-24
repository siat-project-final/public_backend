package com.takoyakki.backend.domain.notification.service;

import com.takoyakki.backend.domain.notification.dto.response.NotificationToMenteeSelectResponseDto;
import com.takoyakki.backend.domain.notification.dto.response.NotificationToMentorSelectResponseDto;

import java.util.List;

public interface NotificationService {
    List<NotificationToMentorSelectResponseDto> selectNotificationToMentor(Long memberId);

    List<NotificationToMenteeSelectResponseDto> selectNotificationToMentee(Long memberId);

    boolean softDeleteNotification(Long notificationId);
}
