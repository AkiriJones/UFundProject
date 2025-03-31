package com.ufund.api.ufundapi.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.Transaction;
import com.ufund.api.ufundapi.model.User;

@Tag("Model-tier")
public class UserTest {
    private User user;
    
    @BeforeEach
    public void setUp() {
        user = new User("User1", new Basket(), new ArrayList<Transaction>());
    }

    @Test
    public void testGetUsername() {
        assertEquals("User1", user.getName());
    }

    @Test
    public void testGetBasket() {
        assertNotNull(user.getBasket());
    }

    @Test
    public void testGetTHistory() {
        assertNotNull(user.fetchTHistory());
    }

    @Test
    public void testNullBasket() {
        User user = new User("User2", null, null);
        assertNotNull(user.getBasket());
        assertNotNull(user.fetchTHistory());
    }
}
