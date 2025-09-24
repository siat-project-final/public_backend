package com.takoyakki.backend.domain.myPage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "회원 정보 조회")
@Builder
public class MemberSelectResponseDto {
    @Schema(description = "member_id")
    private Long memberId;

    @Schema(description = "id")
    private String id;
    @Schema(description = "password")
    private String password;


    @Schema(description = "member_name")
    private String memberName;
    @Schema(description = "role")
    private String role;

    @Schema(description = "email")
    private String email;
    @Schema(description = "nickname")
    private String nickname;
    @Schema(description = "phone_number")
    private String phoneNumber;

    @Schema(description = "current_level")
    private int currentLevel;
    @Schema(description = "usable_points")
    private int usablePoints;
    @Schema(description = "total_xp")
    private int totalXp;

    @Schema(description = "member_image_url")
    private String memberImageUrl;
}
