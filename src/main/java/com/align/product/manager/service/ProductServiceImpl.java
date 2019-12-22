package com.align.product.manager.service;

import com.align.product.manager.configuration.ProductMapper;
import com.align.product.manager.entity.Product;
import com.align.product.manager.exception.ProductNotFoundException;
import com.align.product.manager.model.ProductCreateRequest;
import com.align.product.manager.model.ProductDto;
import com.align.product.manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper  mapperFacade;

    @Override
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(product -> mapperFacade.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllLeftovers() {
        return repository.findAll().stream()
                .filter(product -> product.getQuantity() < 5)
                .map(product -> mapperFacade.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createNewProduct(ProductCreateRequest productCreateRequest) {
        Product createdProduct = repository.save(mapperFacade.map(productCreateRequest, Product.class));
        return mapperFacade.map(createdProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        checkProductExistence(productDto.getId());
        Product updatedProduct = repository.save(mapperFacade.map(productDto, Product.class));
        return mapperFacade.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {
        checkProductExistence(productId);
        repository.deleteById(productId);
    }

    @Override
    public List<ProductDto> findProductsByNameAndBrand(String name, String brand) {
        return repository.findByNameAndBrand(name, brand).stream()
                .map(product -> mapperFacade.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    private void checkProductExistence(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    /*//TODO: to mapper class
    private Product toProductFromRequest(ProductCreateRequest productCreateRequest) {
        mapperFacade.classMap(ProductCreateRequest.class, Product.class);
        MapperFacade mapper = mapperFacade.getMapperFacade();

        return mapper.map(productCreateRequest, Product.class);
    }

    private Product toProductFromDto(ProductDto productDto) {
        mapperFacade.classMap(ProductDto.class, Product.class);
        MapperFacade mapper = mapperFacade.getMapperFacade();

        return mapper.map(productDto, Product.class);
    }

    private ProductDto toProductDto(Product product) {
        mapperFacade.classMap(Product.class, ProductDto.class);
        MapperFacade mapper = mapperFacade.getMapperFacade();

        return mapper.map(product, ProductDto.class);
    }*/
}
