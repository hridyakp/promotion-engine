package com.promo.service;

import com.promo.calculator.PriceCalculator;
import com.promo.calculator.PromotionCalculator;
import com.promo.entity.ItemPromotion;

import java.util.List;
import java.util.stream.Collectors;

public class CalculateService {

    public int calculate(List<ItemPromotion> itemPromotions) {
        if (null != itemPromotions && !itemPromotions.isEmpty()) {
            PriceCalculator baseCalculator = new PriceCalculator();
            List<ItemPromotion> nonPromoItems = itemPromotions.stream().filter(x -> x.getPromotion() == null).collect(Collectors.toList());
            int baseValue = baseCalculator.calculate(nonPromoItems);

            PromotionCalculator promotionCalculator = new PromotionCalculator();
            List<ItemPromotion> promoItems = itemPromotions.stream().filter(x -> x.getPromotion() != null).collect(Collectors.toList());
            int offerValue = promotionCalculator.calculate(promoItems);
            return baseValue + offerValue;
        }
        return 0;
    }
}
