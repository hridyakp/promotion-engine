package com.promo.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Order {
    private List<Item> items;

}
