package com.align.product.manager.configuration;

import com.align.product.manager.entity.Product;
import com.align.product.manager.model.ProductCreateRequest;
import com.align.product.manager.model.ProductDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Product.class, ProductDto.class).byDefault().register();
        factory.classMap(ProductDto.class, Product.class).byDefault().register();
        factory.classMap(ProductCreateRequest.class, Product.class).byDefault().register();
    }
}
