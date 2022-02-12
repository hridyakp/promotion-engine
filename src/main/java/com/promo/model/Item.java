package com.promo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Item {

    private String sku;
    private int quantity;
    private int price;

}
