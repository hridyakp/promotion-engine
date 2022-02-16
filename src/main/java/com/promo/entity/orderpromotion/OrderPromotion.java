package com.promo.entity.orderpromotion;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderPromotion {
    List<NonPromotionalItem> nonPromotionalItems;
    List<ItemPromotion> itemPromotions;
    List<ComboPromotion> comboPromotions;
}
