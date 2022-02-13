package com.promo.service;

import com.promo.calculator.BasePriceCalculator;
import com.promo.calculator.Calculator;
import com.promo.calculator.PromoPriceCalculator;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;
import java.util.Map;

public class CalculateService {
    public int calculateTotalPrice(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap) {
        Calculator calculator;
        if (singlePromoMap.isEmpty() && multiPromoMap.isEmpty()) {
            calculator = new BasePriceCalculator();
        } else {
            calculator = new PromoPriceCalculator();
        }
        return calculator.getOrderValue(order, singlePromoMap, multiPromoMap);
    }
}
