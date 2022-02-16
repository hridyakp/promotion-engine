package com.promo.calculator;

import com.promo.entity.ItemPromotion;

import java.util.List;

public class PriceCalculator {

    public int calculate(List<ItemPromotion> items) {
        int totalValue = 0;
        if (items != null && !items.isEmpty()) {
            for (ItemPromotion item : items) {
                int itemValue = item.getSkuPrice() * item.getQuantity();
                item.setTotalPrice(itemValue);
                totalValue = totalValue + itemValue;
            }
        }
        return totalValue;
    }
}
