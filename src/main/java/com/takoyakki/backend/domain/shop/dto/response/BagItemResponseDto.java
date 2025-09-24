package com.takoyakki.backend.domain.shop.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BagItemResponseDto {
    private Long stickerId;
    private String name;
    private String imageUrl;
    private int slotIndex; // 0~9번 슬롯 위치
}
