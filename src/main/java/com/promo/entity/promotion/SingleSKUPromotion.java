package com.promo.entity.promotion;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SingleSKUPromotion extends Promotion {
    private String promotionId;
    private PromotionItem promotionItem;
    private DiscountType discountType;
    private int value;

    public SingleSKUPromotion(String id, PromotionItem promotionItem, DiscountType discountType, int value) {
        super(PromotionType.SINGLE);
        this.promotionId = id;
        this.promotionItem = promotionItem;
        this.discountType = discountType;
        this.value = value;
    }
}
