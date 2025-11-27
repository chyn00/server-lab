package com.server.lab.concurrencytest.dto;

public record DecreaseStockResponse(
        boolean success,
        String message
) {}
