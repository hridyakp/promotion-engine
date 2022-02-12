package com.promo.service;

import com.promo.model.Item;
import com.promo.model.Order;
import com.promo.model.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PromotionServiceTest {

    @Test
    public void testApplyPromotion() {
        PromotionService service = new PromotionService();

        List<Promotion> promotions = new ArrayList();

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("A", 1, 50);
        Item item2 = new Item("A", 1, 50);
        Item item3 = new Item("A", 1, 50);

        Order order = new Order(items);

        int discountedPrice = service.applyPromotion(order, promotions);
        Assertions.assertEquals(100, discountedPrice);
    }
}
