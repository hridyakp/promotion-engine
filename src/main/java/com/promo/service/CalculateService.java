package com.promo.service;

import com.promo.calculator.MultiPromoCalculator;
import com.promo.calculator.PriceCalculator;
import com.promo.calculator.SinglePromoCalculator;
import com.promo.entity.orderpromotion.ComboPromotion;
import com.promo.entity.orderpromotion.ItemPromotion;
import com.promo.entity.orderpromotion.NonPromotionalItem;
import com.promo.entity.orderpromotion.OrderPromotion;

import java.util.List;

public class CalculateService {

    public float calculate(OrderPromotion orderPromotion) {
        float totalValue = 0;
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

    private float calculateSingleSKUPromotionPrice(List<ItemPromotion> itemPromotions) {
        SinglePromoCalculator calculator = new SinglePromoCalculator();
        float price = calculator.calculate(itemPromotions);
        return price;
    }

    private float calculateMultiSKUPromotionPrice(List<ComboPromotion> comboPromo) {
        MultiPromoCalculator calculator = new MultiPromoCalculator();
        float price = calculator.calculate(comboPromo);
        return price;
    }

    private float calculateNonPromotionalPrice(List<NonPromotionalItem> nonPromoItems) {
        PriceCalculator calculator = new PriceCalculator();
        float price = calculator.calculate(nonPromoItems);
        return price;
    }

}
