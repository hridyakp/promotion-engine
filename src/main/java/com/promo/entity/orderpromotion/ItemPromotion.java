package com.promo.entity.orderpromotion;

import com.promo.entity.promotion.Promotion;
import lombok.Data;

@Data
public class ItemPromotion {
    private String sku;
    private int skuPrice;
    private int quantity;
    private float totalPrice;
    private Promotion promotion;

}
