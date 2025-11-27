package com.server.lab.concurrencytest.repository;

import com.server.lab.concurrencytest.entity.ItemStock;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {

    Optional<ItemStock> findByItemId(Long itemId);

    /**
     * 재고 차감 시 0 미만으로 내려가지 않게 하는 핵심 쿼리.
     *
     * @return 업데이트에 성공한 row 수 (1이면 성공, 0이면 재고 부족)
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE ItemStock s
           SET s.stock = s.stock - :quantity
         WHERE s.itemId = :itemId
           AND s.stock >= :quantity
        """)
    int decreaseStockSafely(@Param("itemId") Long itemId,
                            @Param("quantity") long quantity);
}
