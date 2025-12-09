package com.example.mart.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
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
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

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

    @Test
    @Commit
    public void orderCascadeTest() {
        // 3번 고객이 2번 제품을 구매한다
        Member member = memberRepository.findById(3L).orElseThrow();
        Item item = itemrRepository.findById(2L).orElseThrow();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                // .order(order)
                .orderprice(20000L)
                .count(1L)
                .build();

        // order.getOrderItems().add(orderItem);
        // orderItemRepository.save(orderItem);

        // order 클래스에서 orderItem 추가하는 메소드 추가 후
        order.addOrderItem(orderItem);

        // 밑 set코드는 내가 생각해본 편하게 order에서 orderItem 추가하는 방법
        // order.setOrderItems(List.of(orderItem));
        orderRepository.save(order);

    }

    @Test
    @Commit
    public void updateTest() {
        // 3번 고객의 city 변경
        Member member = memberRepository.findById(3L).get();
        member.setCity("Incheon"); // @Transactional로 인해 dirty checking 했으므로 save 할 필요 x

        // 3번 item 수량 변경
        Item item = itemrRepository.findById(3L).get();
        item.setQuantity(75L);

        // 2번 고객 주문상태 cancel로 변경
        Member member2 = memberRepository.findById(2L).get();
        Order order = orderRepository.findByMember(member2).get(0);
        order.setOrderStatus(OrderStatus.CANCEL);
    }

    @Test
    @Commit
    public void deleteTest() {
        // order, order_item 제거

        // 방법 1 : 자식 삭제 후 부모 삭제
        // orderItemRepository.deleteById(1L);
        // orderRepository.deleteById(1L);

        // 방법 2 : cascade 활용 부모삭제할때 관련된 자식들도 같이 삭제되게 하기
        orderRepository.deleteById(6L);
    }

    @Test
    @Commit
    // @Transactional(readOnly = true)
    public void orphanDeleteTest() {
        // 주문 상태 조회
        Order order = orderRepository.findById(8L).get();
        System.out.println(order);

        // 주문한 상품(item) 조회
        System.out.println(order.getOrderItems());

        // list에서
        order.getOrderItems().remove(0);
        System.out.println("삭제 후 : " + order.getOrderItems());
    }

    @Test
    @Commit
    public void deliveryTest() {
        // order
        Member member = memberRepository.findById(4L).get();
        Item item = itemrRepository.findById(5L).get();
        Delivery delivery = Delivery.builder()
                .city("Seoul")
                .street("hongdae")
                .zipcode("11-4")
                .deliveryStatus(DeliveryStatus.COMP)
                .build();
        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .delivery(delivery)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .count(2L)
                .item(item)
                .orderprice(15000L)
                .order(order)
                .build();

        deliveryRepository.save(delivery);
        order.setOrderItems(List.of(orderItem));
        orderRepository.save(order);

    }

    @Test
    @Commit
    public void cascadeDeliveryTest() {
        // cascade 적용 -> order 저장으로 자식인 delivery까지 저장되게
        Member member = memberRepository.findById(2L).get();
        Item item = itemrRepository.findById(3L).get();
        Delivery delivery = Delivery.builder()
                .city("Seoul")
                .street("hongdae")
                .zipcode("11-4")
                .deliveryStatus(DeliveryStatus.COMP)
                .build();
        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .delivery(delivery)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .count(2L)
                .item(item)
                .orderprice(15000L)
                .order(order)
                .build();

        order.setOrderItems(List.of(orderItem));
        orderRepository.save(order);
    }

    @Test
    @Transactional(readOnly = true)
    public void orderReadTest2() {
        Order order = orderRepository.findById(10L).get();

        // order 조회
        System.out.println(order);

        // 주문한 고객 정보 조회
        System.out.println(order.getMember());
        // 주문한 고객의 이름만 조회
        System.out.println(order.getMember().getName());

        // 주문한 제품 조회
        System.out.println(order.getOrderItems());
        System.out.println(order.getOrderItems().get(0));

        // 배송 조회
        System.out.println("배송 조회 : " + order.getDelivery());
    }

    @Test
    @Transactional(readOnly = true)
    public void memberReadTest() {
        Member member = memberRepository.findById(4L).get();

        // member 조회
        System.out.println(member);

        // 주문 조회
        System.out.println("주문 조회 : " + member.getOrders());

        member.getOrders().forEach(order -> {
            System.out.println(order.getDelivery());
            System.out.println(order.getMember());
            System.out.println(order.getOrderItems());
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void orderItemReadTest() {
        OrderItem orderItem = orderItemRepository.findById(7L).get();

        // orderItem 조회
        System.out.println("orderItem 조회:" + orderItem);

        // 주문 현황 조회
        System.out.println("order 조회:" + orderItem.getOrder());
        Order order = orderItem.getOrder();
        // 배송 조회
        System.out.println("배송 조회:" + order.getDelivery());
        // 고객 정보 조회
        System.out.println("고객 정보 조회:" + order.getMember());

        // 상품 조회
        System.out.println(orderItem.getItem());
    }

    // --------------------------------------------------------------------------
    // @ManyToMany 로 설정했을때
    @Test
    @Commit
    public void categoryTest() {
        Item item = itemrRepository.findById(3L).get();

        Category category = Category.builder().name("가전제품").build();
        // category.getItems().add(item);
        categoryRepository.save(category);

        category = Category.builder().name("생활용품").build();
        // category.getItems().add(item);
        categoryRepository.save(category);
    }

    @Test
    @Transactional(readOnly = true)
    public void categoryReadTest() {
        Category category = categoryRepository.findById(1L).get();

        // category 조회
        System.out.println("category 조회:" + category);

        // category에 속한 아이템 조회
        // System.out.println("category에 속한 아이템 조회" + category.getItems());
    }

    @Test
    @Transactional(readOnly = true)
    public void itemReadTest() {
        Item item = itemrRepository.findById(3L).get();

        // 아이템 조회
        System.out.println(item);

        // 카테고리 조회
        // System.out.println(item.getCategories());
    }

    // ---------------------이하 ManyToOne 설정
    @Test
    @Commit
    public void categoryTest2() {
        Item item = itemrRepository.findById(2L).get();

        Category category = Category.builder().name("신혼용품").build();
        categoryRepository.save(category);

        CategoryItem categoryItem = CategoryItem.builder()
                .category(category)
                .item(item)
                .build();
        categoryItemRepository.save(categoryItem);

        category = categoryRepository.findById(1L).get();
        categoryItem = CategoryItem.builder()
                .category(category)
                .item(item)
                .build();
        categoryItemRepository.save(categoryItem);
    }

    @Test
    public void categoryItemReadTest() {
        CategoryItem categoryItem = categoryItemRepository.findById(1L).orElseThrow();

        // 카테고리아이템 조회
        System.out.println(categoryItem);

        // 카테고리 조회
        System.out.println(categoryItem.getCategory());

        // 아이템 조회
        System.out.println(categoryItem.getItem());

        // 양방향 열고
        Category category = categoryRepository.findById(1L).get();
        System.out.println(category.getCategoryItems());
    }
}
