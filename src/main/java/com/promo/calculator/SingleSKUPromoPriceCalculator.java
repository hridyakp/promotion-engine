package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.SingleSKUPromotion;

public class SingleSKUPromoPriceCalculator {

    public int calculate(ItemPromotion item) {
        SingleSKUPromotion promotion = item.getPromotion() instanceof SingleSKUPromotion ? ((SingleSKUPromotion) item.getPromotion()) : null;
        int reducedSKUPrice;
        if (promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)) {
            reducedSKUPrice = promotion.getValue();
        } else {
            reducedSKUPrice = item.getSkuPrice() * (promotion.getValue() / 100);
        }
        item.setTotalPrice(reducedSKUPrice);
        return reducedSKUPrice;
    }
}
