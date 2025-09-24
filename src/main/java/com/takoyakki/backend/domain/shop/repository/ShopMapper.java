package com.takoyakki.backend.domain.shop.repository;

import com.takoyakki.backend.domain.shop.dto.response.BagItemResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.StickerResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {

    List<StickerResponseDto> getAllStickers();

    List<Long> getPurchasedStickerIds(@Param("memberId") Long memberId);

    int hasSticker(@Param("memberId") Long memberId, @Param("stickerId") Long stickerId); // ✅ 수정된 부분
    int getMemberPoint(@Param("memberId") Long memberId);

    int getStickerCost(@Param("stickerId") Long stickerId);

    void insertMemberSticker(@Param("memberId") Long memberId, @Param("stickerId") Long stickerId);

    void deductMemberPoint(@Param("memberId") Long memberId, @Param("cost") int cost);

    List<StickerResponseDto> getMemberInventory(@Param("memberId") Long memberId);

    List<BagItemResponseDto> getBagItems(@Param("memberId") Long memberId);

    boolean bagSlotExists(@Param("memberId") Long memberId, @Param("stickerId") Long stickerId);

    void insertBagSlot(@Param("memberId") Long memberId, @Param("stickerId") Long stickerId, @Param("slotIndex") int slotIndex);

    void removeBagSlot(@Param("memberId") Long memberId, @Param("slotIndex") int slotIndex);
}
