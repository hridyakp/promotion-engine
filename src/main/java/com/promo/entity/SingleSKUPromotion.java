package com.promo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class SingleSKUPromotion extends Promotion{
    private PromotionItem promotionItem;
    DiscountType discountType;
    private int value;
}
