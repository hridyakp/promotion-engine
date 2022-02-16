package com.promo.service;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.Promotion;
import com.promo.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionServiceDetailedTest {
    @Test
    public void testApplyPromotion1() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 1, 50),
                new Item("B", 1, 30),
                new Item("C", 2, 20),
                new Item("D", 2, 15));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(140, discountedPrice);
    }

    //@Test
    public void testApplyPromotion2() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 1, 50),
                new Item("B", 1, 30),
                new Item("C", 1, 20),
                new Item("D", 2, 15));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(125, discountedPrice);
    }

    @Test
    public void testApplyPromotion3() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 4, 50),
                new Item("B", 3, 30),
                new Item("C", 2, 20),
                new Item("D", 2, 15));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(315, discountedPrice);
    }
}
