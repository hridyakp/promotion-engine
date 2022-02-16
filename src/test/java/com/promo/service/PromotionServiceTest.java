package com.promo.service;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.*;
import com.promo.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionServiceTest {

    @Test
    @DisplayName(value = "Scenario A")
    public void testApplyPromotionScenarioA() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 1, 50),
                new Item("B", 1, 30),
                new Item("C", 1, 20));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(100, discountedPrice);
    }

    @Test
    @DisplayName(value = "Scenario B")
    public void testApplyPromotionScenarioB() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 5, 50),
                new Item("B", 5, 30),
                new Item("C", 1, 20));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(370, discountedPrice);
    }

    @Test
    @DisplayName(value = "Scenario C")
    public void testApplyPromotionScenarioC() {
        PromotionService service = new PromotionService();
        List<Promotion> promotions = TestUtil.getActivePromotions();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", 3, 50),
                new Item("B", 5, 30),
                new Item("C", 1, 20),
                new Item("D", 1, 15));
        Order order = new Order(items);
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(280, discountedPrice);
    }

    @Test
    @DisplayName(value = "Promo applied twice on same set C & D")
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
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(140, discountedPrice);
    }

    @Test
    @DisplayName(value = "MultiPromo and non promotional item in same sku D")
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
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(125, discountedPrice);
    }

    @Test
    @DisplayName(value = "Single sku promotion and multi sku promotion applied multiple times")
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
        float discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(315, discountedPrice);
    }
}
