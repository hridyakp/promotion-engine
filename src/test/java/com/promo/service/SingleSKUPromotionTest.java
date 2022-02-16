package com.promo.service;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.Promotion;
import com.promo.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test class to test different scenarios when more than one single sku promotion applied on a SKU
 * Promotion applied are 4R-> 75%4R (percentage) and  3R-> 60 (fixed price)
 * Promotion with max quantity is applied first
 */
public class SingleSKUPromotionTest {

    @Test
    @DisplayName(value = "No promotion applied")
    public void testApplyPromotion1() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 2, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(50, discountedPrice);
    }

    @Test
    @DisplayName(value = "3R-> 60 (fixed price) applied")
    public void testApplyPromotion2() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 3, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(60, discountedPrice);
    }

    @Test
    @DisplayName(value = "4R-> 75%4R applied")
    public void testApplyPromotion3() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 4, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(75, discountedPrice);
    }

    @Test
    @DisplayName(value = "4R-> 75%4R applied once")
    public void testApplyPromotion4() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 6, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(125, discountedPrice);
    }

    @Test
    @DisplayName(value = "4R-> 75%4R  and 3R-> 60 applied once")
    public void testApplyPromotion5() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 7, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(135, discountedPrice);
    }

    @Test
    @DisplayName(value = "4R-> 75%4R  and 3R-> 60 applied once")
    public void testApplyPromotion6() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("R", 8, 25));
        Order order = new Order(items);
        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(160, discountedPrice);
    }
}
