package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public abstract interface CupboardDAO {
    /**
     * Retrieves all {@linkplain Need need}
     * 
     *  @return An array of {@link Need need}
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need[] getNeeds() throws IOException;

    /**
     * Finds all {@linkplain Need need} whose name matches the text inputted
     * 
     * @param containsString The text that is matched to the Need name
     * 
     * @return An array of {@link Need need} that match the inputted string
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need[] findNeeds(String containsString) throws IOException;

    /**
     * Retrieves a {@linkplain Need need} with the given name
     * 
     * @param name The name of the {@link Need need} to get
     * 
     * @return a {@link Need need} object with the matching name, null if there is
     * no Need object with a matching name
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need getNeed(String name) throws IOException;

    /**
     * Creates and saves a new {@linkplain Need need}
     * 
     * @param need {@linkplain Need need} that is going to be created and saved
     * 
     * @return new {@linkplain Need need} if successful, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need createNeed(Need need) throws IOException;

    /**
     * Updates and saves a {@linkplain Need need}
     * 
     * @param need {@link Need need} object to be updated if successful, null
     * if {@link Need need} can't be found
     * 
     * @return updated {@link Need need} if successful, null if {@link Need need} can't be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need updateNeed(Need need) throws IOException;

    /**
     * Deletes a {@linkplain Need need} with the given name
     * 
     * @param name The name of the {@link Need need}
     * 
     * @return true if the {@link Need need} was deleted,
     * false if the need with th ename doesn't exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteNeed(String name) throws IOException;
}