package com.capitalone.price;

import com.capitalone.price.exception.PriceNotFoundException;
import com.capitalone.price.model.Price;
import com.capitalone.price.repository.PriceRepository;
import com.capitalone.price.service.PriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    @Test
    void getPriceReturnsPricesWhenFound() {
        PriceRepository mockRepository = mock(PriceRepository.class);
        PriceService priceService = new PriceService(mockRepository);

        LocalDateTime date = LocalDateTime.now();
        Price price = new Price();
        List<Price> prices = List.of(price);

        when(mockRepository.findTopByProductIdAndBrandIdAndDateBetween(1, 2, date)).thenReturn(prices);

        List<Price> result = priceService.getPrice(1, 2, date);

        assertThat(result).isEqualTo(prices);
    }

    @Test
    void getPriceThrowsExceptionWhenNoPricesFound() {
        PriceRepository mockRepository = mock(PriceRepository.class);
        PriceService priceService = new PriceService(mockRepository);

        LocalDateTime date = LocalDateTime.now();

        when(mockRepository.findTopByProductIdAndBrandIdAndDateBetween(1, 2, date)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> priceService.getPrice(1, 2, date))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No price found for productId: 1, brandId: 2 at date: " + date);
    }
}