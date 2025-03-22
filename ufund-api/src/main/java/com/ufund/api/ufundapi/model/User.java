package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * Represents a User in the system.
 */
public class User {

    @JsonProperty("name") String name;
    @JsonProperty("basket") Basket basket;
    @JsonProperty("transactionHistory") Transaction[] transactionHistory;

    /**
     * Constructs a User with the specified username and basket.
     *
     * @param name the username of the user
     * @param basket the basket associated with the user; if null, a new Basket is created
     */
    public User(@JsonProperty("name") String name, @JsonProperty("basket") Basket basket) {
        this.name = name;
        this.basket = basket;
        this.transactionHistory = new Transaction[1];

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
        return name;
    }

    /**
     * Gets the basket associated with the user.
     *
     * @return the basket
     */
    public Basket getBasket() {
        return basket;
    }

    /**
     * Gets the user's transaction history.
     *
     * @return the user's transaction history.
     */
    public Transaction[] getTransactionHistory() {
        return transactionHistory;
    }
}
