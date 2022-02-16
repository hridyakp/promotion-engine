package com.promo.entity;

import lombok.Data;

@Data
public class NonPromotionalItem {
    private String sku;
    private int skuPrice;
    private int quantity;
    private int totalPrice;
}
