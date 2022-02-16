package com.promo.calculator;

import com.promo.entity.orderpromotion.NonPromotionalItem;

import java.util.List;

public class PriceCalculator {

    public float calculate(List<NonPromotionalItem> items) {
        float totalValue = 0;
        if (items != null && !items.isEmpty()) {
            for (NonPromotionalItem item : items) {
                float itemValue = item.getSkuPrice() * item.getQuantity();
                item.setTotalPrice(itemValue);
                totalValue = totalValue + itemValue;
            }
        }
        return totalValue;
    }
}
