package com.align.product.manager;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final MapperFactory mapperFactory;

    @Override
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllLeftovers() {
        //TODO: add paging + to repository
        return repository.findAll().stream()
                .filter(product -> product.getQuantity() <= 5)
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return repository.findByName(name).stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return repository.findByBrand(brand).stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createNewProduct(ProductCreateRequest productCreateRequest) {
        Product createdProduct = repository.save(toProductFromRequest(productCreateRequest));
        return toProductDto(createdProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        checkProductExistence(productDto.getId());
         Product updatedProduct = repository.save(toProductFromDto(productDto));
         return toProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        checkProductExistence(productId);
        repository.deleteById(productId);
    }

    private void checkProductExistence(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    //TODO: to mapper class
    private Product toProductFromRequest(ProductCreateRequest productCreateRequest) {
        mapperFactory.classMap(ProductCreateRequest.class, Product.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(productCreateRequest, Product.class);
    }

    private Product toProductFromDto(ProductDto productDto) {
        mapperFactory.classMap(ProductDto.class, Product.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(productDto, Product.class);
    }

    private ProductDto toProductDto(Product product) {
        mapperFactory.classMap(Product.class, ProductDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(product, ProductDto.class);
    }
}
