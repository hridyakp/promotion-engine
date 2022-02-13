package com.promo.calculator;

import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.List;
import java.util.Map;

public interface Calculator {
    public int getOrderValue(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap);
}
