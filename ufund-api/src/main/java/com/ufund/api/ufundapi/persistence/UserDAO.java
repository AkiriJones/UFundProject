package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

/**
 * DAO interface for User operations.
 */
public interface UserDAO {

    /**
     * Retrieves a user by their username.
     * 
     * @param username the name of the user
     * @return the User object if found, otherwise null
     */
    User getUser(String name);
    
    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created User object
     * @throws IOException if an I/O error occurs while creating the user
     */
    User createUser(User user) throws IOException;

    /**
     * Updates an existing user.
     *
     * @param user the user with updated information
     * @return the updated User object
     * @throws IOException if an I/O error occurs while updating the user
     */
    User updateUser(User user) throws IOException;

    /**
     * Retrieves all users.
     *
     * @return an array of all User objects
     */
    User[] getAllUsers();
}
