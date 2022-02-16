package com.promo.mapper;

import com.promo.entity.ComboPromotion;
import com.promo.entity.ItemPromotion;
import com.promo.entity.NonPromotionalItem;
import com.promo.entity.OrderPromotion;
import com.promo.entity.order.Item;
import com.promo.entity.order.Order;
import com.promo.entity.promotion.MultiSKUPromotion;
import com.promo.entity.promotion.Promotion;
import com.promo.entity.promotion.PromotionItem;
import com.promo.entity.promotion.SingleSKUPromotion;
import com.promo.helper.QuantityReverseComparator;

import java.util.*;
import java.util.stream.Collectors;

public class PromotionMapper {

    public OrderPromotion getOrderPromotionMapping(Order order, Map<String, List<SingleSKUPromotion>> singlePromoMap, Set<MultiSKUPromotion> multiPromotions) {
        Map<String, Integer> skuQuantityMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getQuantity));
        Map<String, Integer> skuPriceMap = order.getItems().stream().collect(Collectors.toMap(Item::getSKU, Item::getPrice));
        //Item with Single SKU promotion
        List<ItemPromotion> itemPromotions = mapSingleSKUPromotion(singlePromoMap, skuQuantityMap, skuPriceMap, order.getItems());

        //Item with Multiple SKU promotion
        List<ComboPromotion> comboPromotions = mapMultiSKUPromotion(multiPromotions, skuQuantityMap, skuPriceMap);

        //Item  without promotion
        List<NonPromotionalItem> nonPromoItems = mapProductsWithoutPromotion(skuQuantityMap, skuPriceMap);

        OrderPromotion orderPromotion = OrderPromotion.builder()
                .itemPromotions(itemPromotions)
                .comboPromotions(comboPromotions)
                .nonPromotionalItems(nonPromoItems)
                .build();

        return orderPromotion;
    }

    private List<ComboPromotion> mapMultiSKUPromotion(Set<MultiSKUPromotion> multiPromotions, Map<String, Integer> skuQuantityMap, Map<String, Integer> skuPriceMap) {
        List<ComboPromotion> comboPromotions = new ArrayList<>();
        for (MultiSKUPromotion promotion : multiPromotions) {
            List<PromotionItem> promoItems = promotion.getPromotionItems();
            while (true) {
                boolean available = checkAvailableQuantityOfMultiPromoItem(skuQuantityMap, promoItems);
                //if not available break the while loop else create combo promotion object and update quantity
                if (!available)
                    break;
                ComboPromotion comboPromotion = new ComboPromotion();
                List<Item> items = new ArrayList<>();
                for (PromotionItem promoItem : promoItems) {
                    //create a combo item
                    Item item = new Item(promoItem.getSKU(), promoItem.getQuantity(), skuPriceMap.get(promoItem.getSKU()));
                    items.add(item);
                    //update quantity map
                    int balanceQuantity = skuQuantityMap.get(promoItem.getSKU());
                    skuQuantityMap.put(promoItem.getSKU(), (balanceQuantity - promoItem.getQuantity()));
                }
                comboPromotion.setItems(items);
                comboPromotion.setPromotion(promotion);
                comboPromotions.add(comboPromotion);
            }
        }
        return comboPromotions;
    }

    private List<NonPromotionalItem> mapProductsWithoutPromotion(Map<String, Integer> skuQuantityMap, Map<String, Integer> skuPriceMap) {
        List<NonPromotionalItem> nonPromoItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skuQuantityMap.entrySet()) {
            int price = skuPriceMap.get(entry.getKey());
            int quantity = entry.getValue();
            if (quantity > 0) {
                NonPromotionalItem nonPromoItem = new NonPromotionalItem();
                nonPromoItem.setSku(entry.getKey());
                nonPromoItem.setSkuPrice(price);
                nonPromoItem.setQuantity(quantity);
                nonPromoItems.add(nonPromoItem);
            }
        }
        return nonPromoItems;
    }

    private List<ItemPromotion> mapSingleSKUPromotion(Map<String, List<SingleSKUPromotion>> singlePromoMap, Map<String, Integer> skuQuantityMap, Map<String, Integer> skuPriceMap, List<Item> items) {
        List<ItemPromotion> itemPromotions = new ArrayList<>();
        for (Item item : items) {
            String sku = item.getSKU();
            int skuQuantity = skuQuantityMap.get(sku);
            while (skuQuantity > 0) {
                SingleSKUPromotion singleSKUPromotion = checkValidSinglePromotion(sku, skuQuantity, singlePromoMap.get(sku));
                if (null == singleSKUPromotion)
                    break;
                int price = skuPriceMap.get(sku);
                int quantity = singleSKUPromotion.getPromotionItem().getQuantity();
                ItemPromotion itemPromotion = getItemPromotion(sku, price, quantity, singleSKUPromotion);
                itemPromotions.add(itemPromotion);
                skuQuantity = skuQuantity - singleSKUPromotion.getPromotionItem().getQuantity();
            }
            skuQuantityMap.put(sku, skuQuantity);
        }
        return itemPromotions;
    }

    private ItemPromotion getItemPromotion(String sku, int price, int quantity, Promotion promotion) {
        ItemPromotion itemPromotion = new ItemPromotion();
        itemPromotion.setSku(sku);
        itemPromotion.setSkuPrice(price);
        itemPromotion.setQuantity(quantity);
        itemPromotion.setPromotion(promotion);
        return itemPromotion;
    }

    private SingleSKUPromotion checkValidSinglePromotion(String sku, int skuQuantity, List<SingleSKUPromotion> singleSKUPromotions) {
        if (null != singleSKUPromotions) {
            Collections.sort(singleSKUPromotions, new QuantityReverseComparator());
            Optional<SingleSKUPromotion> promotion = singleSKUPromotions.stream()
                    .filter(x -> x.getPromotionItem().getQuantity() <= skuQuantity)
                    .findFirst();
            if (promotion.isPresent())
                return promotion.get();
        }
        return null;
    }

    private boolean checkAvailableQuantityOfMultiPromoItem(Map<String, Integer> skuQuantityMap, List<PromotionItem> promoItems) {
        for (PromotionItem promoItem : promoItems) {
            int requiredQuantity = promoItem.getQuantity();
            int availableQuantity = skuQuantityMap.get(promoItem.getSKU());
            if (availableQuantity < requiredQuantity) {
                return false;
            }
        }
        return true;
    }
}

