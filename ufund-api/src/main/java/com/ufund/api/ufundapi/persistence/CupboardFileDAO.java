package com.ufund.api.ufundapi.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

@Component
public class CupboardFileDAO implements CupboardDAO {
    
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());
    private Map<String, Need> cupboard;
    private ObjectMapper objectMapper;
    private String filename;

    public CupboardFileDAO(@Value("${cupboard.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    
    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    private Need[] getNeedsArray(String containsText) {
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : cupboard.values()){
            if(containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    private boolean save() throws IOException {
        Need[] needsArray = getNeedsArray();
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }

    private boolean load() throws IOException{
        cupboard = new TreeMap<>();
        Need[] needsArray = objectMapper.readValue(new File(filename), Need[].class);
        for(Need need : needsArray) {
            cupboard.put(need.getName(), need);
        }
        return true;
    }
        
    @Override
    public Need[] getNeeds() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeeds'");
    }

    @Override
    public Need[] findNeeds(String containsString) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findNeeds'");
    }

    @Override
    public Need getNeed(String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeed'");
    }

    @Override
    public Need createNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNeed'");
    }

    @Override
    public Need updateNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateNeed'");
    }

    @Override
    public boolean deleteNeed(String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteNeed'");
    }
}