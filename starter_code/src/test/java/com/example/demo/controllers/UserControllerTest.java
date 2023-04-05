package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.persistence.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp()  {
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository" , userRepository);
        TestUtils.injectObject(userController, "cartRepository" , cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder" , encoder);

    }

    @Test
    public  void create_user_happy_path() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test",user.getUsername());
        assertEquals("thisHashed",user.getPassword());



    }

    @Test
    public void find_By_UserName () {


        User mocKUser = new User();
        mocKUser.setUsername("omar");
        when(userRepository.findByUsername("omar")).thenReturn(mocKUser);
        final ResponseEntity<User> response = userController.findByUserName("omar");

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("omar", user.getUsername());



    }

    @Test
    public void find_By_Id(){
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        final ResponseEntity<User> response = userController.findById(1L);

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(1L, user.getId());


    }






}
