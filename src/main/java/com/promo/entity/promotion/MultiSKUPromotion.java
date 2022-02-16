package com.promo.entity.promotion;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data
@ToString
public class MultiSKUPromotion extends Promotion {
    private String promotionId;
    private List<PromotionItem> promotionItems;
    private DiscountType discountType;
    private int value;

    public MultiSKUPromotion(String id, List<PromotionItem> promotionItem, DiscountType discountType, int value) {
        super(PromotionType.MULTI);
        this.promotionId = id;
        this.promotionItems = promotionItem;
        this.discountType = discountType;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiSKUPromotion that = (MultiSKUPromotion) o;
        return promotionId.equals(that.promotionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), promotionId);
    }
}
