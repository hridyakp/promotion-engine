package com.promo.entity;

import com.promo.entity.promotion.Promotion;
import lombok.Data;

@Data
public class ItemPromotion {
    private String sku;
    private int skuPrice;
    private int quantity;
    private int totalPrice;
    private Promotion promotion;

}
