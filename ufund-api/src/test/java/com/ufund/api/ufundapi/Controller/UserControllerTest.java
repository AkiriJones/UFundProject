package com.ufund.api.ufundapi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.UserController;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO userDao;

    @BeforeEach
    public void setupUserController() {
        userDao = mock(UserDAO.class);
        userController = new UserController(userDao);
    }

    @Test
    public void testGetUser() {
        assertNotNull(userController);
        when(userDao.getUser("User1")).thenReturn(new User("User1", null));
        when(userDao.getUser("User2")).thenReturn(null);

        ResponseEntity<User> response = userController.getUser("User1");
        ResponseEntity<User> response2 = userController.getUser("User2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertNull(response2.getBody());
    }

    @Test
    public void testGetAllUsers() {
        assertNotNull(userController);
        when(userDao.getAllUsers()).thenReturn(new User[1]);

        ResponseEntity<User[]> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateUser() {
        assertNotNull(userController);
        User user = new User("User", null);

        try {
            when(userDao.createUser(user)).thenReturn(user);
            when(userDao.createUser(null)).thenThrow(new IOException());
        }
        catch(IOException e) {}
        ResponseEntity<User> response = userController.createUser(user);
        ResponseEntity<User> response2 = userController.createUser(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response2.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        assertNotNull(userController);
        User user = new User("User", null);
        try {
            when(userDao.updateUser(user)).thenReturn(user);
            when(userDao.updateUser(null)).thenThrow(new IOException());
        }
        catch (IOException e) {}
        ResponseEntity<User> response = userController.updateUser(user);
        ResponseEntity<User> response2 = userController.updateUser(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response2.getStatusCode());
    }
}
