package com.example.jpa.entity;

// import java.time.LocalDateTime;

// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;

import com.example.jpa.entity.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "itemtbl")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item extends BaseEntity {
    // 테이블명 : itemtbl
    // column : 상품코드(@Id)(code - P0001), 상품명(item_nm), 가격(item_price),
    // 재고수량(stock_number), 상세설명(item_detail),
    // 판매상태(item_sell_status / sell, soldout 둘중하나), 등록시간, 수정시간

    @Id
    @Column
    private String code;

    @Column(name = "item_name", nullable = false)
    private String itemNm;

    @Column(name = "price", nullable = false)
    private Long itemPrice;

    @Column(nullable = false)
    private Long stockNumber;

    @Column(name = "detail")
    @Lob
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus sellStatus;

    public void setSellStatus(ItemSellStatus sellStatus) {
        this.sellStatus = sellStatus;
    }

    public void setStockNumber(Long stockNumber) {
        this.stockNumber = stockNumber;
    }
}
