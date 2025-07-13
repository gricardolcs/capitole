package com.capitalone.price.controller;

import com.capitalone.price.config.PriceApi;
import com.capitalone.price.dto.PriceResponseDto;
import com.capitalone.price.model.Price;
import com.capitalone.price.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController implements PriceApi {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    @GetMapping("/prices")
    public ResponseEntity<PriceResponseDto> getPrice(
           @RequestParam String date,
           @RequestParam Integer productId,
           @RequestParam Integer brandId) {

        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss"));
        List<Price> prices = priceService.getPrice(productId, brandId, dateTime);
        if (prices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Price price = prices.get(0);
        if (price == null) {
            return ResponseEntity.notFound().build();
        }
        PriceResponseDto priceResponseDto = mapToDto(price);
        return ResponseEntity.ok(priceResponseDto);
    }

    private PriceResponseDto mapToDto(Price price) {
        PriceResponseDto dto = new PriceResponseDto();
        dto.setProductId(price.getProductId());
        dto.setBrandId(price.getBrandId());
        dto.setPriceList(price.getPriceList());
        dto.setStartDate(price.getStartDate());
        dto.setEndDate(price.getEndDate());
        dto.setPrice(price.getPrice());
        dto.setCurr(price.getCurr());
        return dto;
    }

}