package com.promo.calculator;

import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.DiscountType;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.PromotionItem;
import com.promo.entity.promotion.SingleSKUPromotion;
import com.promo.helper.QuantityComparator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PromoPriceCalculator implements Calculator {
    @Override
    public int getOrderValue(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap) {
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));
        Map<String, Integer> skuPriceMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getPrice));
        List<String> skus = order.getItems().stream().map(Item::getSKU).collect(Collectors.toList());

        int totalValue = 0;

        //Apply Single SKU promotion
        for (String sku : skus) {
            int skuQuantity = skuQuantityMap.get(sku);
            while (skuQuantity > 0) {
                SingleSKUPromotion singleSKUPromotion = checkValidSinglePromotion(sku, skuQuantity, singlePromoMap.get(sku));
                if (null == singleSKUPromotion) {
                    break;
                }
                totalValue = applySingleSKUPromotion(skuPriceMap, totalValue, sku, singleSKUPromotion);
                skuQuantity = skuQuantity - singleSKUPromotion.getPromotionItem().getQuantity();
            }
            skuQuantityMap.put(sku, skuQuantity);
        }

        //Apply Multiple SKU promotion
        for (String sku : skus) {
            int skuQuantity = skuQuantityMap.get(sku);
            while (skuQuantity > 0) {
                List<MultiSKUPromotion> multiSKUPromotions = checkValidMultiPromotion(sku, skuQuantity, multiPromoMap.get(sku));
                if (null == multiSKUPromotions || multiSKUPromotions.isEmpty()) {
                    break;
                }
                int skuPromotionQuantity = 0;
                for (MultiSKUPromotion multiSKUpromotion : multiSKUPromotions) {
                    for (PromotionItem promotionItem : multiSKUpromotion.getPromotionItems()) {
                        if(promotionItem.getSKU().equals(sku)){
                            skuPromotionQuantity= promotionItem.getQuantity();
                        }
                        if (skuQuantityMap.get(promotionItem.getSKU()) < promotionItem.getQuantity()) {
                            break;
                        }
                    }
                    skuQuantity = skuQuantity - skuPromotionQuantity;
                    totalValue = applyMultiSKUPromotion(skuPriceMap, skuQuantityMap, totalValue, multiSKUpromotion);
                }
            }
        }

        //Apply price without promotion
        for(Map.Entry<String, Integer> entry : skuPriceMap.entrySet()){
           int quantity = skuQuantityMap.get(entry.getKey());
           int price = entry.getValue();
           totalValue = totalValue + price * quantity;
        }

        return totalValue;
    }

    private int applyMultiSKUPromotion(Map<String, Integer> skuPriceMap, Map<String, Integer> skuQuantityMap, int totalValue, MultiSKUPromotion multiSKUpromotion) {
        List<PromotionItem> items = multiSKUpromotion.getPromotionItems();
        if (multiSKUpromotion.getDiscountType().equals(DiscountType.FIXED_PRICE)) {
            totalValue = totalValue + multiSKUpromotion.getValue();
            for (PromotionItem item : items) {
                int balanceQuantity = skuQuantityMap.get(item.getSKU());
                skuQuantityMap.put(item.getSKU(), balanceQuantity - item.getQuantity());
            }
        } else {
            int totalItemPrice = 0;
            for (PromotionItem item : items) {
                totalItemPrice = totalItemPrice + skuPriceMap.get(item.getSKU()) * item.getQuantity();
                int balanceQuantity = skuQuantityMap.get(item.getSKU());
                skuQuantityMap.put(item.getSKU(), balanceQuantity - item.getQuantity());
            }
            int reducedSKUPrice = totalItemPrice * (multiSKUpromotion.getValue() / 100);
            totalValue = totalValue + reducedSKUPrice;
        }
        return totalValue;
    }

    private int applySingleSKUPromotion(Map<String, Integer> skuPriceMap, int totalValue, String sku, SingleSKUPromotion singleSKUPromotion) {
        if (singleSKUPromotion.getDiscountType().equals(DiscountType.FIXED_PRICE)) {
            totalValue = totalValue + singleSKUPromotion.getValue();
        } else {
            int reducedSKUPrice = skuPriceMap.get(sku) * (singleSKUPromotion.getValue() / 100);
            totalValue = totalValue + ((reducedSKUPrice) * (singleSKUPromotion.getPromotionItem().getQuantity()));
        }
        return totalValue;
    }

    private SingleSKUPromotion checkValidSinglePromotion(String sku, int skuQuantity, List<SingleSKUPromotion> singleSKUPromotions) {
        if(null != singleSKUPromotions){
            Collections.sort(singleSKUPromotions, new QuantityComparator());
            Optional<SingleSKUPromotion> promotion = singleSKUPromotions.stream().filter(x -> x.getPromotionItem().getQuantity() <= skuQuantity).findFirst();
            if (promotion.isPresent())
                return promotion.get();
        }
         return null;
    }

    private List<MultiSKUPromotion> checkValidMultiPromotion(String sku, int skuQuantity, List<MultiSKUPromotion> multiSKUPromotions) {
        if(null != multiSKUPromotions){
            List<MultiSKUPromotion> promotion = multiSKUPromotions.stream().filter(x -> checkSKUQuantity(sku, skuQuantity, x.getPromotionItems())).collect(Collectors.toList());
             return promotion;
        }
        return null;
    }

    private boolean checkSKUQuantity(String sku, int skuQuantity, List<PromotionItem> promotionItems) {
        for (PromotionItem item : promotionItems) {
            if (item.getSKU().equals(sku) && skuQuantity >= item.getQuantity()) {
                return true;
            }
        }
        return false;
    }


}
