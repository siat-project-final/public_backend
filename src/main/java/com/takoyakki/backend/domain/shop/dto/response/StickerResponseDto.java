package com.takoyakki.backend.domain.shop.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StickerResponseDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int cost;
    private boolean purchased; // 인벤토리 또는 샵에서 구매 여부 표시
}
