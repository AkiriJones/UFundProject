package com.ufund.api.ufundapi.Model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Transaction;

@Tag("Model-tier")
public class TransactionTest {
    private Need[] needs;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        needs = new Need[] {
            new Need(2, "sugar", 2.0, 38, "food"),
            new Need(3, "milk", 2.0, 39, "food"),
        };
        transaction = new Transaction(1, needs);
    }

    @Test
    void testConstructor() {
        assertEquals(1, transaction.getId());
        assertEquals(needs, transaction.getNeeds());
        assertEquals((2.0 * 38) + (2.0 * 39), transaction.getTotal());
        assertNotNull(transaction.getDate());
    }

    @Test
    void testSetNeeds() {
        Need[] newNeeds = { new Need(3, "water", 1.0, 10, "food")};
        transaction.setNeeds(newNeeds);
        assertArrayEquals(newNeeds, transaction.getNeeds());
    }

    @Test 
    void testSetTotal() {
        transaction.setTotal(100.0);
        assertEquals(100.0, transaction.getTotal());
    }

    @Test
    void testGetDate() {
        assertNotNull(transaction.getDate());
    }

    @Test
    void testEquals() {
        Transaction equalTrans = new Transaction(1, needs);
        assertNotEquals(transaction, equalTrans);
    }

    @Test
    void testToString() {
        assertNotNull(transaction.toString());
    }
}
