package com.study.jpa;

import com.study.jpa.entity.*;
import com.study.jpa.exception.NotEnoughStockException;
import com.study.jpa.service.ItemService;
import com.study.jpa.service.MemberService;
import com.study.jpa.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Test
    public void order_item_Success() {
        // given
        Member member = createMember();
        Book item = createBook();
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order findOrder = orderService.findOrder(orderId);

        // then
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(10000 * 2);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void order_item_stock_over_NotEnoughStockException() {
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            // given
            Member member = createMember();
            Item item = createBook();
            int orderCount = 11;

            // when
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    public void cancel_order() {
        // given
        Member member = createMember();
        Item item = createBook();
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);
        Order findOrder = orderService.findOrder(orderId);

        // then
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Member createMember() {
        Member member = new Member();

        member.setName("member1");
        member.setAddress(new Address("Seoul", "Gangnam", "123-123"));

        memberService.join(member);

        return member;
    }

    private Book createBook() {
        Book book = new Book();

        book.setName("Book JPA");
        book.setStockQuantity(10);
        book.setPrice(10000);

        itemService.saveItem(book);

        return book;
    }
}