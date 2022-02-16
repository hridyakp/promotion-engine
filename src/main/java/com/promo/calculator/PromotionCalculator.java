package com.promo.calculator;

import com.promo.entity.ItemPromotion;

import java.util.List;

public class PromotionCalculator {

    public int calculate(List<ItemPromotion> items) {
        int totalValue = 0;
        if (items != null && !items.isEmpty()) {
            SingleSKUPromoPriceCalculator singleSKUCalculator = new SingleSKUPromoPriceCalculator();
            MultiSKUPromoPriceCalculator multiSKUCalculator = new MultiSKUPromoPriceCalculator();
            for (ItemPromotion item : items) {
                int itemValue = 0;
                switch (item.getPromotion().getPromotionType()) {
                    case SINGLE: {
                        itemValue = singleSKUCalculator.calculate(item);
                        break;
                    }
                    case MULTI: {
                        itemValue = multiSKUCalculator.calculate(item);
                        break;
                    }
                }
                totalValue = totalValue + itemValue;
            }
        }
        return totalValue;
    }
}
