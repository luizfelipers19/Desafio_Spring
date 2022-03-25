package com.itboocamp.desafiospring.entity.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductFilter {

    private Long productId;
    private String name;
    private String category;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private Boolean freeShipping;
    private Double prestige;

    @Override
    public String toString() {
        return "ProductFilter{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", freeShipping=" + freeShipping +
                ", prestige=" + prestige +
                '}';
    }


}
