package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {
    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    private static final String USERNAME = "omar";
    private static final int quantity = 5;





    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObject(cartController,"userRepository",userRepository);
        TestUtils.injectObject(cartController,"cartRepository",cartRepository);
        TestUtils.injectObject(cartController,"itemRepository",itemRepository);

        Item item1 = Item.builder()
                .id(1L)
                .name("item1")
                .price(BigDecimal.valueOf(10))
                .description("description")
                .build();

        User user = new User();
        user.setUsername(USERNAME);
        Cart cart = new Cart();
        user.setCart(cart);
        cart.addItem(item1);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));

    }

    @Test
    public void  add_to_cart() {

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setUsername(USERNAME);
        request.setQuantity(quantity);
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void remove_from_cart() {

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(USERNAME);
        request.setItemId(1L);
        request.setQuantity(quantity);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }



}
