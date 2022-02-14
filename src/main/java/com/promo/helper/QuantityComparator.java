package com.promo.helper;

import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.Comparator;

public class QuantityComparator implements Comparator<SingleSKUPromotion> {

    @Override
    public int compare(SingleSKUPromotion o1, SingleSKUPromotion o2) {
        return o1.getPromotionItem().getQuantity() - o2.getPromotionItem().getQuantity();
    }
}
