package com.server.lab.concurrencytest.service;

import com.server.lab.concurrencytest.repository.ItemStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final ItemStockRepository itemStockRepository;

    public StockService(ItemStockRepository itemStockRepository) {
        this.itemStockRepository = itemStockRepository;
    }

    @Transactional
    public boolean decreaseItemStock(Long itemId, long quantity) {
        int updatedRows = itemStockRepository.decreaseStockSafely(itemId, quantity);
        return updatedRows == 1; // true면 성공, false면 재고 부족
    }
}
