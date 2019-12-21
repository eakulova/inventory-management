package com.align.product.manager.repository;

import com.align.product.manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:name is null or p.name = :name) and (:brand is null"
            + " or p.brand = :brand)")
    List<Product> findByNameAndBrand(@Param("name") String name,
                                     @Param("brand") String brand);
}
