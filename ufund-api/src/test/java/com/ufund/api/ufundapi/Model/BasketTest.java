package com.ufund.api.ufundapi.Model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Basket;

@Tag("Model-tier")
public class BasketTest {
    private Basket basket;

    @BeforeEach
    public void setUp() {
        basket = new Basket();
    }

    @Test
    public void testNewBasket() {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 1});

        Basket newBasket = new Basket(list);
        assertNotNull(newBasket.getItems());
    }

    @Test
    public void testDeleteItem() {
        basket.addItem(45, 8);
        basket.addItem(46, 9);

        basket.deleteItem(45);

        List<int[]> items = basket.getItems();
        assertEquals(1, items.size());
    }

    @Test
    public void testDeleteItemNonExistent() {
        basket.addItem(33, 6);
        basket.deleteItem(23);
        List<int[]> items = basket.getItems();
        
        assertEquals(1, items.size());
    }

    @Test
    public void testAddItem() {
        basket.addItem(33, 6);
        List<int[]> items = basket.getItems();

        assertEquals(1, items.size());
    }

    @Test
    public void testIncreaseAmount() {
        basket.addItem(33,2);
        basket.increaseAmount(33, 2);
        List<int[]> items = basket.getItems();
        assertArrayEquals(new int[]{33, 4}, items.get(0));
    }
    
}
