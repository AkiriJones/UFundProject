package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

/**
 * Implementation of UserDAO that manages user data using a file-based approach.
 */
@Component
public class UserFileDAO implements UserDAO {
    
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());

    Map<String,User> users;
    private ObjectMapper objectMapper;
    private String filename;

    /**
     * Constructs a UserFileDAO with the specified filename and ObjectMapper.
     *
     * @param filename the JSON file where user data is stored
     * @param objectMapper the object mapper for JSON processing
     * @throws IOException if an error occurs while loading the file
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads user data from the file into memory.
     *
     * @return true if loading is successful
     * @throws IOException if an error occurs while reading the file
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for(User user : userArray) {
            users.put(user.getUsername(), user);
        }
        return true;
    }

    /**
     * Saves user data from memory to the file.
     *
     * @return true if saving is successful
     * @throws IOException if an error occurs while writing to the file
     */
    private boolean save() throws IOException {
        User[] userArray = getAllUsers();
        objectMapper.writeValue(new File(filename), userArray);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String name) {
        synchronized(users) {
            if(users.containsKey(name)) {
                return users.get(name);
            }
            else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            User newUser = new User(user.getUsername(), user.getBasket(), user.getTransactionHistory());
            users.put(newUser.getUsername(), newUser);
            save();
            return newUser;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if(users.containsKey(user.getUsername()) == false) {
                return null;
            }
            users.put(user.getUsername(), user);
            return user;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User[] getAllUsers() {
        return getAllUsers(null);
    }

    /**
     * Retrieves all users whose usernames contain the specified text.
     *
     * @param containsText the text to search for in usernames
     * @return an array of matching users
     */
    public User[] getAllUsers(String containsText) {
        ArrayList<User> userList = new ArrayList<>();

        for(User user : users.values()) {
            if(containsText == null || user.getUsername().contains(containsText)) {
                userList.add(user);
            }
        }

        User[] userArray = new User[userList.size()];
        userList.toArray(userArray);
        return userArray;
    }

}
