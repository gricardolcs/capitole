package com.capitalone.price.repository;

import com.capitalone.price.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId AND :date BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<Price> findTopByProductIdAndBrandIdAndDateBetween(Integer productId, Integer brandId, LocalDateTime date);

}