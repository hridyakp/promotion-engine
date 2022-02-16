package com.promo.util;

import com.promo.entity.promotion.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public static List<Promotion> getActivePromotions() {
        List<Promotion> promotions = new ArrayList();

        //A, B and C Promotion
        PromotionItem promotionItemA = new PromotionItem("A", 3);
        Promotion promotion1 = new SingleSKUPromotion("A3", promotionItemA, DiscountType.FIXED_PRICE, 130);

        PromotionItem promotionItemB = new PromotionItem("B", 2);
        Promotion promotion2 = new SingleSKUPromotion("B2", promotionItemB, DiscountType.FIXED_PRICE, 45);

        PromotionItem promotionItemC = new PromotionItem("C", 1);
        PromotionItem promotionItemD = new PromotionItem("D", 1);
        ArrayList<PromotionItem> promotionItemsCD = new ArrayList<>();
        Collections.addAll(promotionItemsCD, promotionItemC, promotionItemD);
        Promotion promotion3 = new MultiSKUPromotion("C1D1", promotionItemsCD, DiscountType.FIXED_PRICE, 30);

        //P and Q Promotion
        PromotionItem promotionItemP3 = new PromotionItem("P3", 3);
        Promotion promotion4 = new SingleSKUPromotion("P3", promotionItemP3, DiscountType.FIXED_PRICE, 50);

        PromotionItem promotionItemP2 = new PromotionItem("P2", 2);
        Promotion promotion5 = new SingleSKUPromotion("P2", promotionItemP2, DiscountType.PERCENTAGE, 80);

        PromotionItem promotionItemP1 = new PromotionItem("P", 1);
        PromotionItem promotionItemQ1 = new PromotionItem("Q", 1);
        ArrayList<PromotionItem> promotionItemsPQ = new ArrayList<>();
        Collections.addAll(promotionItemsPQ, promotionItemP1, promotionItemQ1);
        Promotion promotion6 = new MultiSKUPromotion("P1Q1", promotionItemsPQ, DiscountType.FIXED_PRICE, 30);

        //R Promotion
        PromotionItem promotionItemR4 = new PromotionItem("R", 4);
        Promotion promotion7 = new SingleSKUPromotion("R4", promotionItemR4, DiscountType.PERCENTAGE, 75);
        PromotionItem promotionItemR3 = new PromotionItem("R3", 3);
        Promotion promotion8 = new SingleSKUPromotion("R3", promotionItemR3, DiscountType.FIXED_PRICE, 60);

        Collections.addAll(promotions, promotion1, promotion2, promotion3, promotion4, promotion5, promotion6, promotion7);
        return promotions;
    }
}
