package com.capitalone.price.service;

import com.capitalone.price.exception.PriceNotFoundException;
import com.capitalone.price.model.Price;
import com.capitalone.price.repository.PriceRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public  List<Price> getPrice(Integer productId, Integer brandId, LocalDateTime date) {
        List<Price> response = priceRepository.findTopByProductIdAndBrandIdAndDateBetween(productId, brandId, date);
        if (response.isEmpty()) {
            throw new PriceNotFoundException("No price found for productId: " + productId + ", brandId: " + brandId + " at date: " + date);
        }

        return response;
    }


}