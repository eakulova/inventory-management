package com.align.product.manager.controller;

import com.align.product.manager.model.ProductCreateRequest;
import com.align.product.manager.model.ProductDto;
import com.align.product.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//TODO: test
//TODO: Exception handler
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getLeftovers() {
        return productService.getAllLeftovers();
    }

    @GetMapping("/search")
    public List<ProductDto> getProductsByNameAndBrand(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String brand) {
        return productService.findProductsByNameAndBrand(name, brand);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid ProductCreateRequest request) {
        return productService.createNewProduct(request);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        return productService.updateProduct(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProduct(productId);
    }
}
