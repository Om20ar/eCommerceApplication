package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    Item item1 = Item.builder()
            .id(1L)
            .name("item1")
            .price(BigDecimal.valueOf(5))
            .description("description")
            .build();

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObject(itemController,"itemRepository",itemRepository);



    }

    @Test
    public void get_Items() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void get_Item_By_ID() {

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);

    }

    @Test
    public void get_Item_By_Name() {
        when(itemRepository.findByName("item1")).thenReturn(Collections.singletonList(item1));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }







}


