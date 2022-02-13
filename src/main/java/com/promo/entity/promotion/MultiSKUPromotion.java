package com.promo.entity.promotion;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MultiSKUPromotion extends Promotion {
    private List<PromotionItem> promotionItems;
    private DiscountType discountType;
    private int value;

    public MultiSKUPromotion(List<PromotionItem> promotionItem, DiscountType discountType, int value) {
        super( PromotionType.MULTI);
        this.promotionItems = promotionItem;
        this.discountType = discountType;
        this.value = value;
    }
}
