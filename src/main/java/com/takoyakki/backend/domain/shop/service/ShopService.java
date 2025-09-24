package com.takoyakki.backend.domain.shop.service;

import com.takoyakki.backend.domain.shop.dto.request.AddToBagRequestDto;
import com.takoyakki.backend.domain.shop.dto.request.PurchaseRequestDto;
import com.takoyakki.backend.domain.shop.dto.response.BagItemResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.InventoryResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.StickerResponseDto;

import java.util.List;

public interface ShopService {

    List<StickerResponseDto> getAllStickersWithPurchaseStatus(Long memberId);

    boolean purchaseSticker(PurchaseRequestDto dto);

    List<StickerResponseDto> getMemberInventory(Long memberId);

    List<BagItemResponseDto> getBagItems(Long memberId);

    boolean addStickerToBag(AddToBagRequestDto dto);

    int getMemberPoint(Long memberId);

    InventoryResponseDto getInventory(Long memberId);

    void removeFromBag(Long memberId, int slotIndex);
}
