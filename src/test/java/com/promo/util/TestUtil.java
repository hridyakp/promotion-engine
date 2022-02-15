package com.promo.util;

import com.promo.entity.promotion.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public static List<Promotion> getActivePromotions() {
        List<Promotion> promotions = new ArrayList();

        ArrayList<PromotionItem> promotionItems = new ArrayList<>();

        PromotionItem promotionItemA = new PromotionItem("A", 3);
        Promotion promotion1 = new SingleSKUPromotion(promotionItemA, DiscountType.FIXED_PRICE, 130);

        PromotionItem promotionItemB = new PromotionItem("B", 2);
        Promotion promotion2 = new SingleSKUPromotion(promotionItemB, DiscountType.FIXED_PRICE, 45);

        PromotionItem promotionItemC = new PromotionItem("C", 1);
        PromotionItem promotionItemD = new PromotionItem("D", 1);
        Collections.addAll(promotionItems, promotionItemC, promotionItemD);
        Promotion promotion3 = new MultiSKUPromotion(promotionItems, DiscountType.FIXED_PRICE, 30);

        Collections.addAll(promotions, promotion1, promotion2, promotion3);
        return promotions;
    }
}
