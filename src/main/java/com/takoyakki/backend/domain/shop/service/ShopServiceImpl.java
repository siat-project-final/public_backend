package com.takoyakki.backend.domain.shop.service;

import com.takoyakki.backend.domain.shop.dto.request.AddToBagRequestDto;
import com.takoyakki.backend.domain.shop.dto.request.PurchaseRequestDto;
import com.takoyakki.backend.domain.shop.dto.response.BagItemResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.InventoryResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.StickerResponseDto;
import com.takoyakki.backend.domain.shop.repository.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    @Override
    public List<StickerResponseDto> getAllStickersWithPurchaseStatus(Long memberId) {
        System.out.println("📥 [getAllStickersWithPurchaseStatus] 호출됨, memberId = " + memberId);

        List<Long> ownedStickerIds = shopMapper.getPurchasedStickerIds(memberId);
        System.out.println("🔍 [보유 스티커 ID 목록] = " + ownedStickerIds);

        List<StickerResponseDto> allStickers = shopMapper.getAllStickers().stream()
                .map(sticker -> StickerResponseDto.builder()
                        .id(sticker.getId())
                        .name(sticker.getName())
                        .imageUrl(sticker.getImageUrl())
                        .cost(sticker.getCost())
                        .purchased(ownedStickerIds.contains(sticker.getId()))
                        .build())
                .collect(Collectors.toList());

        System.out.println("📦 [전체 스티커 개수] = " + allStickers.size());
        return allStickers;
    }

    @Override
    @Transactional
    public boolean purchaseSticker(PurchaseRequestDto dto) {
        Long memberId = dto.getMemberId();
        Long stickerId = dto.getStickerId();

        System.out.println("🧪 [구매 시도] memberId = " + memberId + ", stickerId = " + stickerId);

        int count = shopMapper.hasSticker(memberId, stickerId);
        System.out.println("🔎 [중복 보유 체크] count = " + count);
        if (count > 0) {
            System.out.println("❗ 이미 보유한 스티커 → 구매 중단");
            return false;
        }

        Integer memberPoint = shopMapper.getMemberPoint(memberId);
        System.out.println("💰 [현재 포인트] = " + memberPoint);
        if (memberPoint == null) {
            System.out.println("❌ 존재하지 않는 회원 → 예외 발생");
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        int cost = shopMapper.getStickerCost(stickerId);
        System.out.println("💸 [스티커 가격] = " + cost);

        if (memberPoint < cost) {
            System.out.println("❗ 포인트 부족 → 구매 불가");
            return false;
        }

        System.out.println("📝 [insert 실행 전]");
        shopMapper.insertMemberSticker(memberId, stickerId);
        System.out.println("✅ insertMemberSticker 실행 완료");

        shopMapper.deductMemberPoint(memberId, cost);
        System.out.println("✅ 포인트 차감 완료");

        return true;
    }

    @Override
    public List<StickerResponseDto> getMemberInventory(Long memberId) {
        System.out.println("📦 [인벤토리 조회 시작] memberId = " + memberId);

        List<StickerResponseDto> result = shopMapper.getMemberInventory(memberId);
        System.out.println("📋 [조회 결과 개수] = " + result.size());
        for (StickerResponseDto s : result) {
            System.out.println("🎟️ 스티커: id = " + s.getId() + ", name = " + s.getName());
        }

        return result.stream()
                .map(s -> StickerResponseDto.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .imageUrl(s.getImageUrl())
                        .cost(s.getCost())
                        .purchased(true)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDto getInventory(Long memberId) {
        System.out.println("📦 [getInventory 호출됨] memberId = " + memberId);

        List<StickerResponseDto> inventory = getMemberInventory(memberId);
        System.out.println("📦 [getInventory 최종 결과] 스티커 수 = " + inventory.size());

        return InventoryResponseDto.builder()
                .memberId(memberId)
                .nickname(null)
                .stickers(inventory)
                .build();
    }

    @Override
    public List<BagItemResponseDto> getBagItems(Long memberId) {
        System.out.println("🎒 [가방 아이템 조회] memberId = " + memberId);

        List<BagItemResponseDto> result = shopMapper.getBagItems(memberId);
        System.out.println("🎒 [가방 슬롯 개수] = " + result.size());
        return result.stream()
                .map(slot -> BagItemResponseDto.builder()
                        .stickerId(slot.getStickerId())
                        .name(slot.getName())
                        .imageUrl(slot.getImageUrl())
                        .slotIndex(slot.getSlotIndex())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStickerToBag(AddToBagRequestDto dto) {
        System.out.println("➕ [가방에 추가 시도] memberId = " + dto.getMemberId() + ", stickerId = " + dto.getStickerId());

        boolean exists = shopMapper.bagSlotExists(dto.getMemberId(), dto.getStickerId());
        if (exists) {
            System.out.println("❗ 이미 가방에 존재 → 추가 불가");
            return false;
        }

        shopMapper.insertBagSlot(dto.getMemberId(), dto.getStickerId(), dto.getSlotIndex());
        System.out.println("✅ 가방에 스티커 추가 완료");
        return true;
    }

    @Override
    public int getMemberPoint(Long memberId) {
        Integer point = shopMapper.getMemberPoint(memberId);
        System.out.println("💰 [포인트 조회] memberId = " + memberId + ", point = " + point);

        if (point == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다. memberId = " + memberId);
        }
        return point;
    }

    @Override
    public void removeFromBag(Long memberId, int slotIndex) {
        System.out.println("❌ [가방에서 제거] memberId = " + memberId + ", slotIndex = " + slotIndex);
        shopMapper.removeBagSlot(memberId, slotIndex);
    }
}
