package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.MultiSKUPromotion;

public class MultiSKUPromoPriceCalculator {
    public int calculate(ItemPromotion item) {
        int reducedSKUPrice = 0;
        if (item.getQuantity() != 0){
            MultiSKUPromotion promotion = item.getPromotion() instanceof MultiSKUPromotion ? ((MultiSKUPromotion) item.getPromotion()) : null;
            if(promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)){
                reducedSKUPrice = promotion.getValue();
            } else {
                reducedSKUPrice = item.getSkuPrice() * (promotion.getValue() / 100);
            }
            item.setTotalPrice(reducedSKUPrice);
        }
        return reducedSKUPrice;
    }
}
