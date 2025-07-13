package com.capitalone.price.config;

import com.capitalone.price.dto.PriceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Prices example", description = "Retrieve product prices based on date, product ID, and brand ID")
public interface PriceApi {

    @Operation(
            summary = "Fetch price for a product",
            description = "fetches price for a product based on the provided date, product ID, and brand ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<PriceResponseDto> getPrice(
            @RequestParam String date,
            @RequestParam Integer productId,
            @RequestParam Integer brandId);
}
