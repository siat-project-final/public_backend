package com.takoyakki.backend.domain.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDto {
    private Long memberId;   // 구매자 ID
    private Long stickerId;  // 구매할 스티커 ID
}
