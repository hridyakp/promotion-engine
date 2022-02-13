package com.promo.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Item {

    private String SKU;
    private int quantity;
    private int price;

}
