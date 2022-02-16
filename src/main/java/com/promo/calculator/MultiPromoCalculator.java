package com.promo.calculator;

import com.promo.entity.orderpromotion.ComboPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.MultiSKUPromotion;

import java.util.List;

public class MultiPromoCalculator {

    public float calculate(List<ComboPromotion> combos) {
        float price = 0;
        for(ComboPromotion combo  : combos){
            float reducedSKUPrice = 0;
            MultiSKUPromotion promotion = combo.getPromotion() instanceof MultiSKUPromotion ? ((MultiSKUPromotion) combo.getPromotion()) : null;
            if(promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)){
                reducedSKUPrice = promotion.getValue();
            } else {
                float itemTotalPrice = combo.getItems().stream().map(Item::getPrice).reduce(Integer::sum).orElse(0);
                reducedSKUPrice = itemTotalPrice * (Float.valueOf(promotion.getValue()) / 100);
            }
            combo.setTotalPrice(reducedSKUPrice);
            price = price + reducedSKUPrice;
        }
        return price;
    }
}
