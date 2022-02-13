package com.promo.service;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.PromotionItem;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.*;
import java.util.stream.Collectors;

public class PromotionService {
    public int applyPromotion(Order order, List<Promotion> activePromotions) {
        List<String> skus = order.getItems().stream().map(Item::getSKU).collect(Collectors.toList());
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));

        List<MultiSKUPromotion> matchingMultiPromotions = activePromotions.stream()
                .filter(obj -> obj instanceof MultiSKUPromotion)
                .map(MultiSKUPromotion.class::cast)
                .filter(x -> getMatchingMultiPromotion(x, skus, skuQuantityMap))
                .collect(Collectors.toList());

        List<SingleSKUPromotion> matchingSinglePromotions = activePromotions.stream()
                .filter(obj -> obj instanceof SingleSKUPromotion)
                .map(SingleSKUPromotion.class::cast)
                .filter(x -> getMatchingSinglePromotion(x, skus, skuQuantityMap))
                .collect(Collectors.toList());

        Map<String, List<SingleSKUPromotion>> singlePromoMap = matchingSinglePromotions.stream()
                .collect(Collectors.groupingBy(x -> x.getPromotionItem().getSKU()));
        Map<String, List<MultiSKUPromotion>> multiPromoMap = getMultiPromotionMap(matchingMultiPromotions);

        CalculateService calculateService = new CalculateService();
        return calculateService.calculateTotalPrice(order, singlePromoMap, multiPromoMap);
    }

    private Map<String, List<MultiSKUPromotion>> getMultiPromotionMap(List<MultiSKUPromotion> matchingMultiPromotions) {
        Map<String, List<MultiSKUPromotion>> map = new HashMap<>();
        for (MultiSKUPromotion promo : matchingMultiPromotions) {
            for (PromotionItem item : promo.getPromotionItems()) {
                if (null == map.get(item.getSKU())) {
                    List<MultiSKUPromotion> promotions = new ArrayList<>();
                    Collections.addAll(promotions, promo);
                    map.put(item.getSKU(), promotions);
                } else {
                    map.get(item.getSKU()).add(promo);
                }
            }
        }
        return map;
    }

    private boolean getMatchingMultiPromotion(MultiSKUPromotion promotion, List<String> SKUs, Map<String, Integer> skuQuantityMap) {
        for (PromotionItem item : promotion.getPromotionItems()) {
            int promotionQuantity = item.getQuantity();
            int orderQuantity = Optional.ofNullable(skuQuantityMap.get(item.getSKU())).orElse(0);
            if (!SKUs.contains(item.getSKU()) && promotionQuantity > orderQuantity) {
                return false;
            }
        }
        return true;
    }

    private boolean getMatchingSinglePromotion(SingleSKUPromotion promotion, List<String> skus, Map<String, Integer> skuQuantityMap) {
        String sku = promotion.getPromotionItem().getSKU();
        int promotionQuantity = promotion.getPromotionItem().getQuantity();
        int orderQuantity = skuQuantityMap.get(sku);
        if (skus.contains(sku) && orderQuantity >= promotionQuantity) {
            return true;
        }
        return false;
    }

}
