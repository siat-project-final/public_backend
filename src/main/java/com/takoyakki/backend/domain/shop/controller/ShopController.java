package com.takoyakki.backend.domain.shop.controller;

import com.takoyakki.backend.domain.shop.dto.request.AddToBagRequestDto;
import com.takoyakki.backend.domain.shop.dto.request.PurchaseRequestDto;
import com.takoyakki.backend.domain.shop.dto.response.StickerResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.BagItemResponseDto;
import com.takoyakki.backend.domain.shop.dto.response.InventoryResponseDto;
import com.takoyakki.backend.domain.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // 0. 사용자 포인트 조회
    @GetMapping("/point")
    public ResponseEntity<Integer> getUserPoint(@RequestParam Long memberId) {
        int point = shopService.getMemberPoint(memberId);
        return ResponseEntity.ok(point);
    }

    // 1. 전체 상점 아이템 (구매 여부 포함)
    @GetMapping("/stickers")
    public List<StickerResponseDto> getAllStickers(@RequestParam Long memberId) {
        return shopService.getAllStickersWithPurchaseStatus(memberId);
    }

    // 2. 스티커 구매
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSticker(@RequestBody PurchaseRequestDto dto) {
        boolean success = shopService.purchaseSticker(dto);
        return success ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("구매 실패 (포인트 부족 또는 중복)");
    }

    // 3. 내가 가진 스티커 (인벤토리)
    @GetMapping("/inventory")
    public InventoryResponseDto getInventory(@RequestParam Long memberId) {
        return shopService.getInventory(memberId);
    }

    // 4. 가방 슬롯 목록 조회
    @GetMapping("/bag")
    public List<BagItemResponseDto> getBag(@RequestParam Long memberId) {
        return shopService.getBagItems(memberId);
    }

    // 5. 가방에 추가
    @PostMapping("/bag/add")
    public ResponseEntity<?> addToBag(@RequestBody AddToBagRequestDto dto) {
        boolean added = shopService.addStickerToBag(dto);
        return added ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("중복 또는 슬롯 초과");
    }

    // 6. 가방에서 제거
    @DeleteMapping("/bag/{slotIndex}")
    public ResponseEntity<?> removeFromBag(@RequestParam Long memberId, @PathVariable int slotIndex) {
        shopService.removeFromBag(memberId, slotIndex);
        return ResponseEntity.ok().build();
    }
}
