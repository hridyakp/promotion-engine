package com.promo.calculator;

import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;
import java.util.Map;

public class PromoPriceCalculator implements Calculator {
    @Override
    public int getOrderValue(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap) {
        return 0;
    }
}
