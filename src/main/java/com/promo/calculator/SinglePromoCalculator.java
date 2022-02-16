package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;

public class SinglePromoCalculator {
    public float calculate(List<ItemPromotion> items) {
        float price = 0;
        for(ItemPromotion item : items){
            SingleSKUPromotion promotion = item.getPromotion() instanceof SingleSKUPromotion ? ((SingleSKUPromotion) item.getPromotion()) : null;
            float reducedSKUPrice;
            if (promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)) {
                reducedSKUPrice = promotion.getValue();
            } else {
                float totalPrice = item.getSkuPrice() * item.getQuantity();
                reducedSKUPrice =  totalPrice * (Float.valueOf(promotion.getValue())/100);
            }
            price = price + reducedSKUPrice;
            item.setTotalPrice(reducedSKUPrice);
        }
        return price;
    }
}
