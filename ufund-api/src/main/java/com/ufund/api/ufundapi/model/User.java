package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User in the system.
 */
public class User {

    @JsonProperty("username") String username;
    @JsonProperty("basket") Basket basket;

    /**
     * Constructs a User with the specified username and basket.
     *
     * @param username the username of the user
     * @param basket the basket associated with the user; if null, a new Basket is created
     */
    public User(@JsonProperty("username") String username, @JsonProperty("basket") Basket basket) {
        this.username = username;
        this.basket = basket;

        if(basket == null) {
            this.basket = new Basket();
        }
    }

    /**
     * Gets the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the basket associated with the user.
     *
     * @return the basket
     */
    public Basket gBasket() {
        return basket;
    }
}
