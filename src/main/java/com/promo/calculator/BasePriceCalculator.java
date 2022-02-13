package com.promo.calculator;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;
import java.util.Map;

public class BasePriceCalculator implements Calculator {
    @Override
    public int getOrderValue(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap) {
        int totalValue = 0;
        int c = order.getItems().stream().mapToInt(x -> x.getQuantity() * x.getPrice()).sum();
        for (Item item : order.getItems()) {
            int itemValue = item.getPrice() * item.getQuantity();
            totalValue = totalValue + itemValue;
        }
        return totalValue;    }
}
