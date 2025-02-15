package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public interface NeedDAO {

    Need createNeed(Need need) throws IOException;

    Need getNeed(String name) throws IOException;

    Need updateNeed(Need need) throws IOException;

    boolean deleteNeed(String name) throws IOException;

}
