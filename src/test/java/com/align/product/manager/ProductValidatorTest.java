package com.align.product.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductValidatorTest {

    private Validator validator;


    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateProductDtoSuccessfully() {
        ProductDto productDto = new ProductDto()
                .setId(1L)
                .setBrand("brand")
                .setName("name")
                .setPrice(BigDecimal.ZERO);

        Set<ConstraintViolation<ProductDto>> result = validator.validate(productDto);
        assertTrue(result.isEmpty());
    }


    @Test
    void shouldValidateProductCreateRequestSuccessfully() {
        ProductCreateRequest request = new ProductCreateRequest()
                .setBrand("brand")
                .setName("name")
                .setPrice(BigDecimal.ZERO);

        Set<ConstraintViolation<ProductCreateRequest>> result = validator.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldValidateProductDtoWithErrorsWhenAllFieldsAreNull() {
        Set<ConstraintViolation<ProductDto>> result = validator.validate(new ProductDto());
        assertThat(result, containsInAnyOrder(
                hasProperty("messageTemplate", is("field 'id' mustn't be null")),
                hasProperty("messageTemplate", is("field 'name' must be filled")),
                hasProperty("messageTemplate", is("field 'brand' must be filled")),
                hasProperty("messageTemplate", is("field 'price' mustn't be null"))
        ));
    }

    @Test
    void shouldValidateProductCreateRequestWithErrorsWhenAllFieldsAreNull() {
        Set<ConstraintViolation<ProductCreateRequest>> result = validator.validate(new ProductCreateRequest());
        assertThat(result, containsInAnyOrder(
                hasProperty("messageTemplate", is("field 'name' must be filled")),
                hasProperty("messageTemplate", is("field 'brand' must be filled")),
                hasProperty("messageTemplate", is("field 'price' mustn't be null"))
        ));
    }

    @Test
    void shouldValidateProductDtoWithErrorsWhenFieldsAreIncorrect() {
        ProductDto productDto = new ProductDto()
                .setId(1L)
                .setName("")
                .setBrand("")
                .setQuantity(-1)
                .setPrice(BigDecimal.valueOf(-15.1568));

        Set<ConstraintViolation<ProductDto>> result = validator.validate(productDto);

        assertThat(result, containsInAnyOrder(
                hasProperty("messageTemplate", is("field 'name' must be filled")),
                hasProperty("messageTemplate", is("field 'brand' must be filled")),
                hasProperty("messageTemplate", is("field 'price' must be greater or equal to 0")),
                hasProperty("messageTemplate", is("field 'price' must have 2 fraction digits")),
                hasProperty("messageTemplate", is("field 'quatnity' must be greater or equal to 0"))
        ));
    }

    @Test
    void shouldValidateProductCreateRequestWithErrorsWhenFieldsAreIncorrect() {
        ProductCreateRequest request = new ProductCreateRequest()
                .setName("")
                .setBrand("")
                .setQuantity(-1)
                .setPrice(BigDecimal.valueOf(-15.1568));

        Set<ConstraintViolation<ProductCreateRequest>> result = validator.validate(request);

        assertThat(result, containsInAnyOrder(
                hasProperty("messageTemplate", is("field 'name' must be filled")),
                hasProperty("messageTemplate", is("field 'brand' must be filled")),
                hasProperty("messageTemplate", is("field 'price' must be greater or equal to 0")),
                hasProperty("messageTemplate", is("field 'price' must have 2 fraction digits")),
                hasProperty("messageTemplate", is("field 'quatnity' must be greater or equal to 0"))
        ));
    }
}
