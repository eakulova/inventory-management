package com.align.product.manager;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super(String.format("There are no any products with id = %s", id));
    }
}
