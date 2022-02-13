package com.promo.entity.promotion;

import com.promo.entity.promotion.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    protected PromotionType promotionType;
}
