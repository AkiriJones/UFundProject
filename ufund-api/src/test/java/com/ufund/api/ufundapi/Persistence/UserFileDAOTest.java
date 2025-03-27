package com.ufund.api.ufundapi.Persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserFileDAO;

@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO userDao;
    User[] users;
    ObjectMapper mapper;
    
    @BeforeEach
    public void setUp() throws IOException {
        mapper = mock(ObjectMapper.class);
        users = new User[2];

        users[0] = new User("User1", new Basket(), null);
        users[1] = new User("User2", new Basket(), null);

        when(mapper.readValue(new File("file.txt"), User[].class)).thenReturn(users);
        userDao = new UserFileDAO("file.txt", mapper);
    }

    @Test
    public void testGetUser() {
        assertNotNull(userDao.getUser("User1"));
    }

    @Test
    public void testGetNonExistantUser() {
        assertNull(userDao.getUser("null"));
    }

    @Test
    public void testCreateUser() {
        User user = new User("User3", new Basket(), null);
        User created = assertDoesNotThrow(() -> userDao.createUser(user), "Exception thrown");

        assertNotNull(created);

        User actual = userDao.getUser("User3");
        assertEquals(created.getUsername(), actual.getUsername());
        assertEquals(created.getBasket(), actual.getBasket());
        assertEquals(created.getTransactionHistory(), actual.getTransactionHistory());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("User1", new Basket(), null);

        User updated = assertDoesNotThrow(() -> userDao.updateUser(user), "Exception thrown");

        assertNotNull(updated);

        User actual = userDao.getUser("User1");
        assertEquals(updated, actual);
    }

    @Test
    public void testUpdateNonExistantUser() {
        User user = new User("User98", new Basket(), null);
        User updated = assertDoesNotThrow(() -> userDao.updateUser(user), "Exception thrown");

        assertNull(updated);
    }

}
