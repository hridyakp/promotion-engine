package com.promo.calculator;

import com.promo.entity.ItemPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.PromotionItem;
import com.promo.entity.promotion.SingleSKUPromotion;
import com.promo.helper.QuantityComparator;

import java.util.*;
import java.util.stream.Collectors;

public class ItemPromotionMapper {

    public List<ItemPromotion> getOrderValue(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, List<MultiSKUPromotion>> multiPromoMap) {
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));
        Map<String, Integer> skuPriceMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getPrice));
        List<String> skus = order.getItems().stream().map(Item::getSKU).collect(Collectors.toList());

        List<ItemPromotion> itemPromotions = new ArrayList<>();

        //Apply Single SKU promotion
        for (String sku : skus) {
            int skuQuantity = skuQuantityMap.get(sku);
            while (skuQuantity > 0) {
                SingleSKUPromotion singleSKUPromotion = checkValidSinglePromotion(sku, skuQuantity, singlePromoMap.get(sku));
                if (null == singleSKUPromotion) {
                    break;
                }
                ItemPromotion itemPromotion = new ItemPromotion();
                itemPromotion.setSku(sku);
                itemPromotion.setSkuPrice(skuPriceMap.get(sku));
                itemPromotion.setQuantity(singleSKUPromotion.getPromotionItem().getQuantity());
                itemPromotion.setPromotion(singleSKUPromotion);
                itemPromotions.add(itemPromotion);
                //totalValue = applySingleSKUPromotion(skuPriceMap, totalValue, sku, singleSKUPromotion);
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
                    for(PromotionItem item: multiSKUpromotion.getPromotionItems()){
                        int balanceQuantity = skuQuantityMap.get(item.getSKU());
                        ItemPromotion itemPromotion = new ItemPromotion();
                        itemPromotion.setSku(item.getSKU());
                        itemPromotion.setSkuPrice(skuPriceMap.get(sku));
                        itemPromotion.setQuantity(item.getQuantity());
                        itemPromotion.setPromotion(multiSKUpromotion);
                        itemPromotions.add(itemPromotion);
                        skuQuantityMap.put(item.getSKU(), balanceQuantity - item.getQuantity());

                    }
                    skuQuantity = skuQuantity - skuPromotionQuantity;
                    //totalValue = applyMultiSKUPromotion(skuPriceMap, skuQuantityMap, totalValue, multiSKUpromotion);
                }
            }
        }

        //Apply price without promotion
        for(Map.Entry<String, Integer> entry : skuQuantityMap.entrySet()){
           int price = skuPriceMap.get(entry.getKey());
           int quantity = entry.getValue();
           if(quantity>0){
               ItemPromotion itemPromotion = new ItemPromotion();
               itemPromotion.setSku(entry.getKey());
               itemPromotion.setSkuPrice(price);
               itemPromotion.setQuantity(quantity);
               itemPromotion.setPromotion(null);
               itemPromotions.add(itemPromotion);
           }
        }
        return itemPromotions;
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
