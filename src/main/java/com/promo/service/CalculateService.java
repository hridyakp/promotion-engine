package com.promo.service;

import com.promo.calculator.MultiPromoCalculator;
import com.promo.calculator.PriceCalculator;
import com.promo.calculator.SinglePromoCalculator;
import com.promo.entity.ComboPromotion;
import com.promo.entity.ItemPromotion;
import com.promo.entity.NonPromotionalItem;
import com.promo.entity.OrderPromotion;

import java.util.List;

public class CalculateService {

    public int calculate(OrderPromotion orderPromotion) {
        int totalValue = 0;
        if (!orderPromotion.getItemPromotions().isEmpty()) {
            //get total price on skus with single sku promo
            totalValue = totalValue + calculateSingleSKUPromotionPrice(orderPromotion.getItemPromotions());
        }
        if (!orderPromotion.getComboPromotions().isEmpty()) {
            //get total price on skus with combo promo
            totalValue = totalValue + calculateMultiSKUPromotionPrice(orderPromotion.getComboPromotions());
        }
        if (!orderPromotion.getNonPromotionalItems().isEmpty()) {
            //get total price on skus without promo
            totalValue = totalValue + calculateNonPromotionalPrice(orderPromotion.getNonPromotionalItems());
        }
        return totalValue;
    }

    private int calculateSingleSKUPromotionPrice(List<ItemPromotion> itemPromotions) {
        SinglePromoCalculator calculator = new SinglePromoCalculator();
        int price = calculator.calculate(itemPromotions);
        return price;
    }

    private int calculateMultiSKUPromotionPrice(List<ComboPromotion> comboPromo) {
        MultiPromoCalculator calculator = new MultiPromoCalculator();
        int price = calculator.calculate(comboPromo);
        return price;

    }

    private int calculateNonPromotionalPrice(List<NonPromotionalItem> nonPromoItems) {
        PriceCalculator calculator = new PriceCalculator();
        int price = calculator.calculate(nonPromoItems);
        return price;
    }

}
