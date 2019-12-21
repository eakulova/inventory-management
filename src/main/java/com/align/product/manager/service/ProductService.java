package com.align.product.manager.service;

import com.align.product.manager.model.ProductCreateRequest;
import com.align.product.manager.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllLeftovers();

    ProductDto createNewProduct(ProductCreateRequest product);

    ProductDto updateProduct(ProductDto product);

    void deleteProduct(Long productId);

    List<ProductDto> findProductsByNameAndBrand(String name, String brand);
}
