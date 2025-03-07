package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

/**
 * REST Controller that handles user-related operations.
 * Provides endpoints to create, retrieve, update users, and retrieve all users.
 */
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDao;

    /**
     * Constructs a UserController with the provided UserDAO instance.
     * 
     * @param userDao the UserDAO instance to interact with the user data layer
     */
    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Endpoint to create a new user.
     * 
     * @param user the user object to be created
     * @return a ResponseEntity containing the created user and HTTP status OK if successful, or an INTERNAL_SERVER_ERROR if an exception occurs
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            return new ResponseEntity<User>(userDao.createUser(user), HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to retrieve all users.
     * 
     * @return a ResponseEntity containing an array of all users and HTTP status OK
     */
    @GetMapping("")
    public ResponseEntity<User[]> getAllUsers() {
        LOG.info("GET /");
        return new ResponseEntity<User[]>(userDao.getAllUsers(), HttpStatus.OK);   
    }

    /**
     * Endpoint to update an existing user.
     * 
     * @param user the user object containing updated information
     * @return a ResponseEntity containing the updated user and HTTP status OK if successful, or an INTERNAL_SERVER_ERROR if an exception occurs
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users ");
        try {
            return new ResponseEntity<User>(userDao.updateUser(user), HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to retrieve a user by their username.
     * 
     * @param name the username of the user to be retrieved
     * @return a ResponseEntity containing the user if found, or HTTP status NOT_FOUND if the user does not exist
     */
    @GetMapping("/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        LOG.info("GET /users/" + name);
        User user = userDao.getUser(name);
        if(user != null) {
            return new ResponseEntity<User>(user,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
