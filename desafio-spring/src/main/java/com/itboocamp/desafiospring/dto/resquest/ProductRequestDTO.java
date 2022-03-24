package com.itboocamp.desafiospring.dto.resquest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {

    private Long productId;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private Boolean freeShipping;
    private Double prestige;



}