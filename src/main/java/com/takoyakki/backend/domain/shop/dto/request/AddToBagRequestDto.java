package com.takoyakki.backend.domain.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToBagRequestDto {
    private Long memberId;    // 사용자 ID
    private Long stickerId;   // 담을 스티커 ID
    private int slotIndex;    // 가방 슬롯 위치 (0~9)
}
