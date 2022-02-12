package com.promo.service;

import com.promo.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionServiceTest {

    @Test
    public void testApplyPromotion() {
        PromotionService service = new PromotionService();

        List<Promotion> promotions = new ArrayList();

        ArrayList<PromotionItem> promotionItems = new ArrayList<>();

        PromotionItem promotionItemA = new PromotionItem("A", 3);
        Promotion promotion1 = new SingleSKUPromotion(promotionItemA, DiscountType.FIXED_PRICE, 130);

        PromotionItem promotionItemB = new PromotionItem("B", 2);
        Promotion promotion2 = new SingleSKUPromotion(promotionItemA, DiscountType.FIXED_PRICE, 45);

        PromotionItem promotionItemC = new PromotionItem("C", 1);
        PromotionItem promotionItemD = new PromotionItem("D", 1);
        Collections.addAll(promotionItems, promotionItemC, promotionItemD);
        Promotion promotion3 = new MultiSKUPromotion(promotionItems, DiscountType.FIXED_PRICE, 45);

        Collections.addAll(promotions, promotion1, promotion2, promotion3);

        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 1, 50),
                new Item("B", 1, 30),
                new Item("C", 1, 20));

        Order order = new Order(items);

        int discountedPrice = service.applyPromotion(order, promotions);

        Assertions.assertEquals(100, discountedPrice);
    }
}
