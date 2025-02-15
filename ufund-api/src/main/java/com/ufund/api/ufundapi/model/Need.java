package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Need entity
 * 
 * @author Akiri Jones, Jack Faro, Caden Estermann, Sebastian Canakis Diaz, Giulia Spier
 */
public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    static final String STRING_FORMAT = "Need [name=%s, cost=%d, quantity=%d, type=%s, id=%d]";

    @JsonProperty("name") private String name;
    @JsonProperty("cost") private double cost;
    @JsonProperty("quantity")private int quantity;
    @JsonProperty("type") private String type;
    /**
     * Create a Need with a given name
     * @param name The name of the need
     * @param cost The cost of the need
     * @param quantity The amount of said need present 
     * @param type The type of need (Physical or Monetary)
     */
    public Need(@JsonProperty("name") String name, @JsonProperty("cost") double cost, @JsonProperty("quantity") int quantity, @JsonProperty("type") String type){
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }
    /**
     * Retrieves the name of a need
     * @return The name of the need
     */
    public String getName() {return name;}
    /**
     * Sets the name of a need - neccessary for deserialization
     * @param name The name of a need
     */
    public void setName(String name) {this.name = name;}
    /**
     * Retrieves the cost of a need
     * @return The cost of the need
     */
    public Double getCost() {return cost;}
    /**
     * Sets the cost of a need - neccessary for deserialization
     * @param cost the cost of a need
     */
    public void setCost(double cost){this.cost = cost;}
    /**
     * Retrieves the quantity of a need
     * @return The quantity of the need
     */
    public int getQuantity() {return quantity;}
    /**
     * Sets the quantity of a - neccessary for deserialization
     * @param quantity The quantity of the need
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}
    

    // ENUM MUST BE IMPLEMENTED IN THE FUTURE FOR ENHANCMENTS. (Monetary, Physical)
    public String getType() {return type;}
    public void setType(String type){this.type = type;}

    @Override
    public String toString(){
        return String.format(STRING_FORMAT,name,cost,quantity,type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Need need = (Need) obj;
        return name.equals(need.name);
    }
}
