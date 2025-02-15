package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cupboard {
    

    // ONlY ONE CUPBOARD
    // CONTAINS AN ARRAY OF NEEDS
    
    @JsonProperty("needs") private Need[] needs;

    public Cupboard(Need[] needs) {
        this.needs = needs;
    }

    public Need getNeedByName(String needString) {

        for(int i = 0; i < needs.length; i++) {
            if(needs[i].getName() == needString) {
                return needs[i];
            }
        } 

        return null;


    }






}
