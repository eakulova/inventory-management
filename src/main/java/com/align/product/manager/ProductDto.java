package com.align.product.manager;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductDto {

    @NotNull(message = "field 'id' mustn't be null")
    private Long id;

    @NotBlank(message = "field 'name' must be filled")
    private String name;

    @NotBlank(message = "field 'brand' must be filled")
    private String brand;

    @NotNull(message = "field 'price' mustn't be null")
    @PositiveOrZero(message = "field 'price' must be greater or equal to 0")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2,
            message = "field 'price' must have 2 fraction digits")
    private BigDecimal price;

    @PositiveOrZero(message = "field 'quatnity' must be greater or equal to 0")
    private long quantity;
}
