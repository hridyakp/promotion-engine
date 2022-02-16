package com.promo.finder;

import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.SingleSKUPromotion;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SingleSKUPromotionFinder {

    public static Map<String, List<SingleSKUPromotion>> getPromotions(List<Promotion> activePromotions, List<String> skus, Map<String, Integer> skuQuantityMap) {
        List<SingleSKUPromotion> matchingSinglePromotions = activePromotions.stream()
                .filter(obj -> obj instanceof SingleSKUPromotion)
                .map(SingleSKUPromotion.class::cast)
                .filter(x -> getMatchingSinglePromotion(x, skus, skuQuantityMap))
                .collect(Collectors.toList());
        //Collections.sort(matchingSinglePromotions, new);
        Map<String, List<SingleSKUPromotion>> singleSKUPromotionMap = matchingSinglePromotions.stream()
                .collect(Collectors.groupingBy(x -> x.getPromotionItem().getSKU()));
        return singleSKUPromotionMap;
    }

    private static boolean getMatchingSinglePromotion(SingleSKUPromotion promotion, List<String> skus, Map<String, Integer> skuQuantityMap) {
        String sku = promotion.getPromotionItem().getSKU();
        int promotionQuantity = promotion.getPromotionItem().getQuantity();
        if(skus.contains(sku)){
            int orderQuantity = skuQuantityMap.get(sku);
            if (orderQuantity >= promotionQuantity) {
                return true;
            }
        }
        return false;
    }
}
