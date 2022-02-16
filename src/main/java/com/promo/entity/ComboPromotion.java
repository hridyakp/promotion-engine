package com.promo.entity;

import com.promo.entity.order.Item;
import com.promo.entity.promotion.Promotion;
import lombok.Data;

import java.util.List;

@Data
public class ComboPromotion {
    List<Item> items;
    private float totalPrice;
    private Promotion promotion;

}
