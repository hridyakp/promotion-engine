package com.promo.service;

import com.promo.entity.orderpromotion.OrderPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.SingleSKUPromotion;
import com.promo.finder.MultiSKUPromotionFinder;
import com.promo.finder.SingleSKUPromotionFinder;
import com.promo.mapper.PromotionMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PromotionService {
    public float applyPromotion(Order order, List<Promotion> activePromotions) {
        List<String> skus = order.getItems().stream().map(Item::getSKU).collect(Collectors.toList());
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));

        Map<String, List<SingleSKUPromotion>> singlePromoMap = SingleSKUPromotionFinder.getPromotions(activePromotions, skus, skuQuantityMap);
        Set<MultiSKUPromotion> multiPromoSet = MultiSKUPromotionFinder.getUniquePromotions(activePromotions, skus, skuQuantityMap);

        PromotionMapper promotionMapper = new PromotionMapper();
        OrderPromotion orderPromotion = promotionMapper.getOrderPromotionMapping(order, singlePromoMap, multiPromoSet);

        CalculateService calculateService = new CalculateService();
        float totalPrice = calculateService.calculate(orderPromotion);

        return totalPrice;
    }

}
