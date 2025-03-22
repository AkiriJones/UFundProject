package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Transaction {

    public static final String STRING_FORMAT = "Transaction [id=%d, needs=%s, total=%.2f, date=%s]";
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

    /**
     * Retrieves the ID of a transaction.
     * @return The id of the transaction.
     */

    public int getId() {
        return id;
    }

    /**
     * Retrieves the needs in a transaction.
     * @return The array of needs in the transaction.
     */

    public Need[] getNeeds() {
        return needs;
    }

    /**
     * Sets the needs in a transaction.
     * @param needs The needs desired to buy.
     */

    public void setNeeds(Need[] needs) {
        this.needs = needs;
    }

    /**
     * Retrieves the total cost from a transaction.
     * @return The total cost of the transaction.
     */

    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of a transaction.
     * @param total The desired total cost.
     */

    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Retrieves the date a transaction was made.
     * @return The date the transaction was made.
     */

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        String string = String.format(STRING_FORMAT,id, needs, total, date);
        return string;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Transaction transaction = (Transaction) obj;

        return this.id == transaction.id &&
               this.date == transaction.date &&
               Double.compare(transaction.total, this.total) == 0 &&
               needs.equals(transaction.needs);
    }
}
