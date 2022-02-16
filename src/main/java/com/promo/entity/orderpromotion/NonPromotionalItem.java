package com.promo.entity.orderpromotion;

import lombok.Data;

@Data
public class NonPromotionalItem {
    private String sku;
    private int skuPrice;
    private int quantity;
    private float totalPrice;
}
