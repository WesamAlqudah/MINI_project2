package com.project.order.utilities;

import com.project.order.domain.Product;

import java.math.BigDecimal;

public class CartUtility {

    public static BigDecimal getSubTotalForItem(Product product, int quantity){
        return (product.getPrice()).multiply(BigDecimal.valueOf(quantity));
    }
}
