package com.promo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class MultiSKUPromotion extends Promotion{
    private List<PromotionItem> promotionItem;
    DiscountType discountType;
    private int value;
}
