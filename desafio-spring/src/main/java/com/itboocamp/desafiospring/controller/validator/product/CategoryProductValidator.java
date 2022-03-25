package com.itboocamp.desafiospring.controller.validator.product;

import com.itboocamp.desafiospring.controller.exception.ValidatorException;
import com.itboocamp.desafiospring.controller.validator.IValidator;
import com.itboocamp.desafiospring.entity.Product;

public class CategoryProductValidator implements IValidator {
    private Product product;

    public CategoryProductValidator(Product product) {
        this.product = product;
    }

    @Override
    public void validator() throws ValidatorException {
        if(product.getCategory().isEmpty() || product.getCategory().length() > 3) {
            throw new ValidatorException("Campo categoria precisa ser preenchido e conter mais de 3 caracteres.");
        }

    }
}