package com.promo.service;

import com.promo.mapper.ItemPromotionMapper;
import com.promo.entity.ItemPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.SingleSKUPromotion;
import com.promo.finder.MultiSKUPromotionFinder;
import com.promo.finder.SingleSKUPromotionFinder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PromotionService {
    public int applyPromotion(Order order, List<Promotion> activePromotions) {
        List<String> skus = order.getItems().stream().map(Item::getSKU).collect(Collectors.toList());
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));
        Map<String, List<MultiSKUPromotion>> multiPromoMap = MultiSKUPromotionFinder.getPromotions(activePromotions, skus, skuQuantityMap);
        Map<String, List<SingleSKUPromotion>> singlePromoMap = SingleSKUPromotionFinder.getPromotions(activePromotions, skus, skuQuantityMap);
        ItemPromotionMapper matcher = new ItemPromotionMapper();
        List<ItemPromotion> itemPromotions = matcher.getOrderValue(order, singlePromoMap, multiPromoMap);
        CalculateService calculateService = new CalculateService();
        return calculateService.calculateTotalPrice(order, singlePromoMap, multiPromoMap);
    }

}
