package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cupboard {
    
    // ONlY ONE CUPBOARD
    // CONTAINS AN ARRAY OF NEEDS
    
    @JsonProperty("needs") private Need[] needs;

    public Cupboard(Need[] needs) {
        this.needs = needs;
    }

    /**
     * Returns Need that matches the inputted string
     * @param needString The string inputted that tries to match with a Need
     */
    public Need getNeedByName(String needString) {

        for(int i = 0; i < needs.length; i++) {
            if(needs[i].getName() == needString) {
                return needs[i];
            }
        } 
        return null;
    }

    /**
     * Updates a Need by creating a new Need that replaces a current Need
     * @param Name The Need's name
     * @param cost The price of the Need
     * @param quantity The amount 
     * @param type The type of Need
     */
    public void updateNeed(String name, double cost, int quantity, String type) {
        Need need = new Need(name, cost, quantity, type);
        
        
        
    }






}
