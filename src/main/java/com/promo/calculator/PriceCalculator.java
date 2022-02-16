package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.NonPromotionalItem;

import java.util.List;

public class PriceCalculator {

    public int calculate1(List<ItemPromotion> items) {
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

    public int calculate(List<NonPromotionalItem> items) {
        int totalValue = 0;
        if (items != null && !items.isEmpty()) {
            for (NonPromotionalItem item : items) {
                int itemValue = item.getSkuPrice() * item.getQuantity();
                item.setTotalPrice(itemValue);
                totalValue = totalValue + itemValue;
            }
        }
        return totalValue;
    }
}
