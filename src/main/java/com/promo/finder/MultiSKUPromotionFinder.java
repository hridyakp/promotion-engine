package com.promo.finder;

import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.PromotionItem;

import java.util.*;
import java.util.stream.Collectors;

public class MultiSKUPromotionFinder {
    public static Set<MultiSKUPromotion> getUniquePromotions(List<Promotion> activePromotions, List<String> skus, Map<String, Integer> skuQuantityMap) {
        Set<MultiSKUPromotion> uniqueMultiPromotions = activePromotions.stream()
                .filter(obj -> obj instanceof MultiSKUPromotion)
                .map(MultiSKUPromotion.class::cast)
                .filter(x -> getMatchingMultiPromotion(x, skus, skuQuantityMap))
                .collect(Collectors.toSet());
        return uniqueMultiPromotions;
    }

    public static Map<String, List<MultiSKUPromotion>> getPromotions(List<Promotion> activePromotions, List<String> skus, Map<String, Integer> skuQuantityMap) {
        List<MultiSKUPromotion> matchingMultiPromotions = activePromotions.stream()
                .filter(obj -> obj instanceof MultiSKUPromotion)
                .map(MultiSKUPromotion.class::cast)
                .filter(x -> getMatchingMultiPromotion(x, skus, skuQuantityMap))
                .collect(Collectors.toList());
        return getMultiPromotionMap(matchingMultiPromotions);
    }

    private static boolean getMatchingMultiPromotion(MultiSKUPromotion promotion, List<String> SKUs, Map<String, Integer> skuQuantityMap) {
        for (PromotionItem item : promotion.getPromotionItems()) {
            int promotionQuantity = item.getQuantity();
            int orderQuantity = Optional.ofNullable(skuQuantityMap.get(item.getSKU())).orElse(0);
            if (!SKUs.contains(item.getSKU()) && promotionQuantity > orderQuantity) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, List<MultiSKUPromotion>> getMultiPromotionMap(List<MultiSKUPromotion> matchingMultiPromotions) {
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
}
