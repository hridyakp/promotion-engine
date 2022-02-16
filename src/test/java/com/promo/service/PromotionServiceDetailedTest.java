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
 * Test class to test different scenarios when same sku has different combination of promotions active
 * Promotion applied are 4P-> 60 (fixed) and P+Q-> 30 (fixed) and 2P->80%2P (percentage)
 * Same SKU promo Promotion with max quantity applied first
 * Combo promotions are applied on remaining item
 */
public class PromotionServiceDetailedTest {

    @Test
    @DisplayName(value = "P+Q-> 30 (fixed)")
    public void testApplyPromotion1() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 1, 20),
                new Item("Q", 1, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(30, discountedPrice);
    }

    @Test
    @DisplayName(value = "2P->80%2P")
    public void testApplyPromotion2() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 2, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(62, discountedPrice);
    }

    @Test
    @DisplayName(value = "2P->80%2P and P+Q->30")
    public void testApplyPromotion3() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 3, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(77, discountedPrice);
    }

    @Test
    @DisplayName(value = "4P-> 60 (fixed)")
    public void testApplyPromotion4() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 4, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(90, discountedPrice);
    }

    @Test
    @DisplayName(value = "4P-> 60 (fixed) and P+Q-> 30 (fixed)")
    public void testApplyPromotion5() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 5, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(105, discountedPrice);
    }

    @Test
    @DisplayName(value = "4P-> 60 (fixed) and 2P->80%P")
    public void testApplyPromotion6() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 6, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(122, discountedPrice);
    }

    @Test
    @DisplayName(value = "4P-> 60 (fixed) and 2P->80%2P (32) and P+Q-> 30 (fixed)")
    public void testApplyPromotion7() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("P", 7, 20),
                new Item("Q", 2, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(137, discountedPrice);
    }
}
