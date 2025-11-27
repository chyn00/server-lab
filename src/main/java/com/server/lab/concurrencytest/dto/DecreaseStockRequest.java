package com.server.lab.concurrencytest.dto;

public record DecreaseStockRequest(
        Long itemId,
        long quantity
) {}
