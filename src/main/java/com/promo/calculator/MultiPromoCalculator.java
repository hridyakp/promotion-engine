package com.promo.calculator;

import com.promo.entity.ComboPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.MultiSKUPromotion;

import java.util.List;

public class MultiPromoCalculator {

    public int calculate(List<ComboPromotion> combos) {
        int price = 0;
        for(ComboPromotion combo  : combos){
            int reducedSKUPrice = 0;
            MultiSKUPromotion promotion = combo.getPromotion() instanceof MultiSKUPromotion ? ((MultiSKUPromotion) combo.getPromotion()) : null;
            if(promotion.getDiscountType().equals(DiscountType.FIXED_PRICE)){
                reducedSKUPrice = promotion.getValue();
            } else {
                int itemTotalPrice = combo.getItems().stream().map(Item::getPrice).reduce(Integer::sum).orElse(0);
                reducedSKUPrice = itemTotalPrice * (promotion.getValue() / 100);
            }
            combo.setTotalPrice(reducedSKUPrice);
            price = price + reducedSKUPrice;
        }
        return price;
    }
}
