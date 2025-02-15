package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    static final String STRING_FORMAT = "Need [name=%s, cost=%d, quantity=%d, type=%s, id=%d]";

    @JsonProperty("name") private String name;
    @JsonProperty("cost") private double cost;
    @JsonProperty("quantity")private int quantity;
    @JsonProperty("type") private String type;

    public Need(@JsonProperty("name") String name, @JsonProperty("cost") double cost, @JsonProperty("quantity") int quantity, @JsonProperty("type") String type){
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Double getCost() {return cost;}
    public void setCost(double cost){this.cost = cost;}
    public int getQuantity() {return quantity;}
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
