package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.*;

public interface CupboardDAO {

    Need[] getNeeds() throws IOException;

    Need[] findNeeds(String containsString) throws IOException;

    Need getNeed(String name) throws IOException;

    Need createNeed(Need need) throws IOException;

    Need updateNeed(Need need) throws IOException;

    boolean deleteNeed(String name) throws IOException;
}