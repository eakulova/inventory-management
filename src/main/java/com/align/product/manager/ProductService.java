package com.align.product.manager;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllLeftovers();

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getProductsByBrand(String brand);

    ProductDto createNewProduct(ProductCreateRequest product);

    ProductDto updateProduct(ProductDto product);

    void deleteProduct(Long productId);
}
