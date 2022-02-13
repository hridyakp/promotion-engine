package com.promo.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PromotionItem {
    private String SKU;
    private int quantity;
}
