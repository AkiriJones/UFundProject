package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a basket that holds a list of needs to be purchased.
 */
public class Basket {
    @JsonProperty private List<int[]> items;

    /**
     * Default constructor initializes empty basket
     */
    public Basket() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructs a basket with a given list of items.
     * 
     * @param items List of item arrays, where each array consists of {needId, amount}
     */
    public Basket(ArrayList<int[]> items) {
        this.items = items;
    }

    /**
     * Retrieves the list of items in the basket.
     * 
     * @return List of int arrays, where each array contains {needId, amount}.
     */
    public List<int[]> getItems() {
        return items;
    }

    /**
     * Adds an item to the basket.
     * 
     * @param needId the ID of the need.
     * @param amount the amount of the need to be added.
     */
    public void addItem(int needId, int amount) {
        int[] item = {needId, amount};
        items.add(item);
    }

    /**
     * Deletes an item from the basket by its needId.
     * 
     * @param needId the ID of the need to remove.
     */
    public void deleteItem(int needId) {
        for(int i = 0; i < items.size() - 1; i++){
            if(items.get(i)[0] == needId){
                items.remove(i);
            }
        }
    }

    /**
     * Increases the amount of an existing item in the basket.
     * 
     * @param needId the ID of the need.
     * @param amount the amount of said need to be added to the existing amount. 
     */
    public void increaseAmount(int needId, int amount) {
        for(int[] item : items){
            if(item[0] == needId){
                item[1] += amount;
            }
        }
    }
}
