package com.example.jpa.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.constant.ItemSellStatus;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Item item = Item.builder()
            .code("P00"+i)
            .itemPrice(10000L*i)
            .stockNumber(10L)
            .itemDetail("item detail"+i)
            .sellStatus(ItemSellStatus.SELL)
            .itemNm("item"+i)
            .build();

            itemRepository.save(item);
        }
    }

    @Test
    public void updateTest(){
        // item 상태 변경
        Item item = itemRepository.findById("P002").get();
        item.setSellStatus(ItemSellStatus.SOLDOUT);
        itemRepository.save(item);
    }

    @Test
    public void updateTest2(){
        // 재고수량 변경 
        Item item = itemRepository.findById("P006").get();
        item.setStockNumber(4L);
        itemRepository.save(item);
    }

    @Test
    public void deleteTest(){
        itemRepository.deleteById("P008");
    }

    @Test
    public void readTest(){
        System.out.println(itemRepository.findById("P009"));
    }

    @Test
    public void readAllTest(){
        itemRepository.findAll().forEach(item -> System.out.println(item));
    }


}
