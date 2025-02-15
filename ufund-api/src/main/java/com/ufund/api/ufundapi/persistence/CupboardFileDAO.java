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
        synchronized(cupboard) {
            return getNeedsArray();
        }
    }

    @Override
    public Need[] findNeeds(String containsString) throws IOException {
        synchronized(cupboard) {
            return getNeedsArray(containsString);
        }
    }

    @Override
    public Need getNeed(String name) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(name)) {
                return cupboard.get(name);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public Need createNeed(Need need) throws IOException {
        synchronized(cupboard) {
            Need newNeed = new Need(need.getName(), need.getCost(), need.getQuantity(), need.getType());
            cupboard.put(need.getName(),newNeed);
            save();
            return newNeed;
        }
    }

    @Override
    public Need updateNeed(Need need) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(need.getName()) == false) {
                return null;
            }

            cupboard.put(need.getName(),need);
            save();
            return need;
        }
    }

    @Override
    public boolean deleteNeed(String name) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(name)) {
                cupboard.remove(name);
                return save();
            }
            else {
                return false;
            }
        }
    }
}