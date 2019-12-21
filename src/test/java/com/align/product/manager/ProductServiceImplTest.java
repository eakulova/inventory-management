package com.align.product.manager;

import com.align.product.manager.util.ProductRepositoryMock;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryMock();
        productService = new ProductServiceImpl(productRepository, new DefaultMapperFactory.Builder().build());
    }

    @Test
    void shouldReturnListOfAllProducts() {
        prepareProducts();

        List<ProductDto> allProducts = productService.getAllProducts();

        assertThat(allProducts, hasSize(3));
    }

    @Test
    void shouldReturnListOfLeftovers() {
        prepareProducts();

        List<ProductDto> leftovers = productService.getAllLeftovers();

        assertThat(leftovers, hasSize(2));
    }

    @Test
    void shouldCreateProduct() {
        ProductCreateRequest createRequest = new ProductCreateRequest()
                .setName("productCreate")
                .setBrand("brandCreate")
                .setPrice(BigDecimal.TEN)
                .setQuantity(5);
        ProductDto productDto = productService.createNewProduct(createRequest);

        assertAll(
                () -> assertThat(productRepository.findAll(), hasSize(1)),
                () -> assertThat(productDto.getId(), is(notNullValue())),
                () -> assertThat(productDto.getName(), is(createRequest.getName())),
                () -> assertThat(productDto.getBrand(), is(createRequest.getBrand())),
                () -> assertThat(productDto.getPrice(), is(createRequest.getPrice())),
                () -> assertThat(productDto.getQuantity(), is(createRequest.getQuantity()))
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        prepareProducts();
        int initialSize = productRepository.findAll().size();
        ProductDto productToUpdate = new ProductDto()
                .setId(1L)
                .setBrand("newBrand")
                .setName("newName")
                .setPrice(BigDecimal.valueOf(50))
                .setQuantity(100);
        ProductDto updatedProduct = productService.updateProduct(productToUpdate);

        assertAll(
                () -> assertThat(productRepository.findAll(), hasSize(initialSize)),
                () -> assertThat(updatedProduct.getId(), is(productToUpdate.getId())),
                () -> assertThat(updatedProduct.getName(), is(productToUpdate.getName())),
                () -> assertThat(updatedProduct.getBrand(), is(productToUpdate.getBrand())),
                () -> assertThat(updatedProduct.getPrice(), is(productToUpdate.getPrice())),
                () -> assertThat(updatedProduct.getQuantity(), is(productToUpdate.getQuantity()))
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdateProductWithInvalidId() {
        ProductDto productToUpdate = new ProductDto()
                .setId(1L)
                .setBrand("newBrand")
                .setName("newName")
                .setPrice(BigDecimal.valueOf(50))
                .setQuantity(100);

        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(productToUpdate));
        assertEquals(thrown.getMessage(), "There are no any products with id = " + productToUpdate.getId());
    }

    @Test
    void shouldReturnProductsByName() {
        prepareProducts();
        String name = "product1";
        List<ProductDto> products = productService.getProductsByName(name);
        assertAll(
                () -> assertThat(products, hasSize(1)),
                () -> assertThat(products.get(0).getName(), is(name))
        );
    }

    @Test
    void shouldReturnProductsByBrand() {
        prepareProducts();
        String brand = "brand1";
        List<ProductDto> products = productService.getProductsByBrand(brand);
        assertAll(
                () -> assertThat(products, hasSize(1)),
                () -> assertThat(products.get(0).getBrand(), is(brand))
        );
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        prepareProducts();
        List<Product> products = productRepository.findAll();
        int initialSize = products.size();
        Product productToDelete = products.get(0);

        productService.deleteProduct(productToDelete.getId());
        assertAll(
                () -> assertThat(productRepository.findAll(), hasSize(initialSize - 1)),
                () -> assertFalse(productRepository.findById(productToDelete.getId()).isPresent())
        );
    }

    @Test
    void shouldThrowExceptionWhenDeleteWithInvalidId() {
        long id = 5;
        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(id));
        assertEquals(thrown.getMessage(), "There are no any products with id = " + id);
    }

    private void prepareProducts() {
        Product product1 = new Product()
                .setName("product1")
                .setBrand("brand1")
                .setPrice(BigDecimal.ONE)
                .setQuantity(1);
        Product product2 = new Product()
                .setName("product2")
                .setBrand("brand2")
                .setPrice(BigDecimal.TEN)
                .setQuantity(2);
        Product product3 = new Product()
                .setName("product3")
                .setBrand("brand3")
                .setPrice(BigDecimal.ZERO)
                .setQuantity(50);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }
}