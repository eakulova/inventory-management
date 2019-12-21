package com.align.product.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Valid
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getLeftovers() {
        return productService.getAllLeftovers();
    }

    @GetMapping
    public List<ProductDto> getProductsByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping
    public List<ProductDto> getProductsByBrand(@RequestParam String brand) {
        return productService.getProductsByBrand(brand);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createNewProduct(request);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProduct(productId);
    }
}
