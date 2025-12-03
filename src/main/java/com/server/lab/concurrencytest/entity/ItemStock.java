package com.server.lab.concurrencytest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_stock")
public class ItemStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 실제 비즈니스 키 (상품 ID)
    @Column(name = "item_id", nullable = false, unique = true)
    private Long itemId;

    @Column(name = "stock", nullable = false)
    private Long stock;

    protected ItemStock() {
    }

    public ItemStock(Long itemId, Long initialStock) {
        this.itemId = itemId;
        this.stock = initialStock;
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getStock() {
        return stock;
    }

    // ※ 실제 감소는 DB에서 원자적으로 처리할 거라 엔티티에서는 굳이 차감 안 함.
    // DB UPDATE ... WHERE stock >= ? 로 -1 방어를 함.
}
