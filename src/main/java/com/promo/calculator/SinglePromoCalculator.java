package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;

public class SinglePromoCalculator {
    public int calculate(List<ItemPromotion> items) {
        int price = 0;
        for(ItemPromotion item : items){
            SingleSKUPromotion promotion = item.getPromotion() instanceof SingleSKUPromotion ? ((SingleSKUPromotion) item.getPromotion()) : null;
            int reducedSKUPrice;
            if (promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)) {
                reducedSKUPrice = promotion.getValue();
            } else {
                reducedSKUPrice = item.getSkuPrice() * (promotion.getValue() / 100);
            }
            price = price + reducedSKUPrice;
            item.setTotalPrice(reducedSKUPrice);
        }
        return price;
    }
}
