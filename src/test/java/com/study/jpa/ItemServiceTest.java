package com.study.jpa;

import com.study.jpa.entity.Item;
import com.study.jpa.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Test
    public void item_save_Success() {
        // given
        Item item = new Item();
        item.setName("item1");

        // when
        Long saveId = itemService.saveItem(item);
        Item findItem = itemService.findItem(saveId);

        // then
        assertThat(findItem).isEqualTo(item);
    }
}