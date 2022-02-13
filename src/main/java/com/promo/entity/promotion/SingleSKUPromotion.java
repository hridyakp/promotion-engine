package com.promo.entity.promotion;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SingleSKUPromotion extends Promotion {
    private PromotionItem promotionItem;
    private DiscountType discountType;
    private int value;

    public SingleSKUPromotion(PromotionItem promotionItem, DiscountType discountType, int value) {
        super(PromotionType.SINGLE);
        this.promotionItem = promotionItem;
        this.discountType = discountType;
        this.value = value;
    }
}
