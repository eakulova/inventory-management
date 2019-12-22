package com.align.product.manager.controller;

import com.align.product.manager.model.ProductCreateRequest;
import com.align.product.manager.model.ProductDto;
import com.align.product.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//TODO: security test
//TODO: init data
//TODO: Exception handler
//TODO: java docs
//TODO: swagger(?)
//TODO: docker
@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getLeftovers() {
        log.info("Request to get all leftovers");
        return productService.getAllLeftovers();
    }

    @GetMapping("/search")
    public List<ProductDto> getProductsByNameAndBrand(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String brand) {
        log.info("Request to get product by name = {} and brand = {}", name, brand);
        return productService.findProductsByNameAndBrand(name, brand);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid ProductCreateRequest request) {
        log.info("Request to create new product with name = {}", request.getName());
        return productService.createNewProduct(request);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        log.info("Request to update product with id = {}", product.getId());
        return productService.updateProduct(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") long productId) {
        log.info("Request to delete product with id = {}", productId);
        productService.deleteProduct(productId);
    }
}
