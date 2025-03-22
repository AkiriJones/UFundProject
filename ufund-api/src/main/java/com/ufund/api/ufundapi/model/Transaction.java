package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Transaction {
    @JsonProperty("id") private int id;
    @JsonProperty("needs") private Need[] needs;
    @JsonProperty("total") private double total;
    @JsonProperty("date") private Date date;

    /**
     * Create a Transaction
     * @param id the id of a transaction
     * @param needs All the needs a helper purchased
     */

     public Transaction(@JsonProperty("id") int id, @JsonProperty("needs") Need[] needs){
        this.id = id;
        this.needs = needs;
        //init, bool, operator
        for(int i = 0; i < this.needs.length; i++)
        {
            this.total += (needs[i].getCost() * needs[i].getQuantity());
        }
        this.date = new Date();
    }
}
