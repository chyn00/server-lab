package com.server.lab.concurrencytest.api;

import com.server.lab.concurrencytest.dto.DecreaseStockRequest;
import com.server.lab.concurrencytest.dto.DecreaseStockResponse;
import com.server.lab.concurrencytest.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * 재고 감소 API
     * - URL: POST /api/v1/stocks/decrease
     * - Body: { "itemId": 1, "quantity": 1 }
     * - 성공: 200 OK, { "success": true, "message": "OK" }
     * - 재고 부족: 409 CONFLICT, { "success": false, "message": "OUT_OF_STOCK" }
     */
    @PostMapping("/decrease")
    public ResponseEntity<DecreaseStockResponse> decreaseStock(@RequestBody DecreaseStockRequest request) {
        boolean success = stockService.decreaseItemStock(request.itemId(), request.quantity());

        if (success) {
            return ResponseEntity.ok(new DecreaseStockResponse(true, "OK"));
        } else {
            // 재고 부족 등의 이유로 업데이트 실패
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new DecreaseStockResponse(false, "OUT_OF_STOCK"));
        }
    }

    @GetMapping
    public String aaa(){
        return "s";
    }
}
