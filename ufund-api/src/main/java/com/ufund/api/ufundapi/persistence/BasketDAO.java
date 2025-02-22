package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public abstract interface BasketDAO {
    /**
     * Retrieves all {@linkplain Need need}
     * 
     *  @return An array of {@link Need need}
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need[] getNeeds() throws IOException;
}
