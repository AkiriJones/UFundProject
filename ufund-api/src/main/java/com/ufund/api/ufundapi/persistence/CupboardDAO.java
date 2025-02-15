package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.*;

public abstract interface CupboardDAO {

    Need[] getNeeds() throws IOException;

    Need[] findNeeds(String containsString) throws IOException;

    Need getNeed(String name) throws IOException;

    Need createNeed(String name, int cost, int quantity, String type) throws IOException;

    Need updateNeed(String name) throws IOException;

    boolean deleteNeed(String name) throws IOException;
}