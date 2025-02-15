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
    @JsonProperty("id") private int id;

    public Need(@JsonProperty("name") String name, @JsonProperty("cost") double cost, @JsonProperty("quantity") int quantity, @JsonProperty("type") String type, @JsonProperty("id") int id){
        
    }
}
