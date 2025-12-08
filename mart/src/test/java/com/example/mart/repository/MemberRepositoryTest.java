package com.example.mart.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.OrderStatus;
import com.example.mart.repository.ItemrRepository;
import com.example.mart.repository.MemberRepository;
import com.example.mart.repository.OrderItemRepository;
import com.example.mart.repository.OrderRepository;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemrRepository itemrRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Commit
    public void insertMemberTest() {
        // 5명
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("김도훈" + i)
                    .city("Seoul")
                    .street("Jongro")
                    .zipcode("zipcode" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    @Commit
    public void insertItemTest() {
        // 5개
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("item" + i)
                    .price((long) (10000 + i))
                    .quantity((long) (40 * i))
                    .build();
            itemrRepository.save(item);
        });
    }

    @Test
    @Commit
    public void orderTest() {
        // 주문

        // 1번 상품을 2번 고객이 주문
        Member member = memberRepository.findById(2L).orElseThrow();
        Item item = itemrRepository.findById(1L).orElseThrow();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderprice(20000L)
                .count(1L)
                .build();
        orderItemRepository.save(orderItem);
    }

    @Test
    @Transactional(readOnly = true)
    public void orderReadTest() {
        // 주문내역조회
        // 2번 member의 주문내역 뽑기

        // 1번방법(수업)
        // Member member = Member.builder().id(2L).build();
        // 2번방법
        Member member = memberRepository.findById(2L).get();
        Order order = orderRepository.findByMember(member).get(0);
        System.out.println(order);

        // 주문 현황 조회
        order.getOrderItems().forEach(System.out::println);

        // 주문상품의 상세정보 조회
        order.getOrderItems().forEach(i -> {
            System.out.println(i.getItem());
        });
        System.out.println("어차피 하나인데 " + order.getOrderItems().get(0).getItem());

        // 주문한 고객의 상세정보
        System.out.println(order.getMember());
    }
}
