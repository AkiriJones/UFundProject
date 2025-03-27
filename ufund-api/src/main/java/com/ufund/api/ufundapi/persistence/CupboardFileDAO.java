package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Implements the functionality for JSON file-based persistence for Needs
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Akiri Jones, Jack Faro, Caden Esterman, Sebastian Canakis Diaz, Giulia Spier
 */
@Component
public class CupboardFileDAO implements CupboardDAO {
    
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());
    public Map<Integer, Need> cupboard;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Creates a Need File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * @throws IOException when file cannot be accessed or read from
     */
    public CupboardFileDAO(@Value("${cupboard.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Need need}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }
    
    /**
     * Generates an array of {@linkplain Need needs} from the tree map
     * @return  The array of {@link Need needs}, may be empty
     */
    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    /**
     * Generates an array of {@linkplain Need needs} from the tree map for any
     * {@linkplain Need needs} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Need needs}
     * in the tree map
     * 
     * @param containsText
     * @return  The array of {@link Need needs}, may be empty
     */
    private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
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

    /**
     * Saves the {@linkplain Need needs} from the map into the file as an array of JSON objects
     * @return true if the {@link Need needs} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needsArray = getNeedsArray();
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }

    /**
     * Loads {@linkplain Need needs} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException{
        cupboard = new TreeMap<>();
        nextId = 0;

        Need[] needsArray = objectMapper.readValue(new File(filename), Need[].class);
        for(Need need : needsArray) {
            cupboard.put(need.getId(), need);
            if(need.getId() > nextId)
                nextId = need.getId();
        }
        ++nextId;
        return true;
    }
        
    /**
    ** {@inheritDoc}
     */
    @Override
    public Need[] getNeeds() throws IOException {
        synchronized(cupboard) {
            return getNeedsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Need[] findNeeds(String containsString) throws IOException {
        synchronized(cupboard) {
            return getNeedsArray(containsString);   
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Need getNeed(int id) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(id)) {
                return cupboard.get(id);
            }
            else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Need createNeed(Need need) throws IOException {
        synchronized(cupboard) {
            Need newNeed = new Need(nextId(), need.getName(), need.getCost(), need.getQuantity(), need.getType(), need.getLocation(filename));
            cupboard.put(need.getId(),newNeed);
            save();
            return newNeed;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Need updateNeed(Need need) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(need.getId()) == false) {
                return null;
            }
            cupboard.put(need.getId(),need);
            save();
            return need;   
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteNeed(int id) throws IOException {
        synchronized(cupboard) {
            if(cupboard.containsKey(id)) {
                cupboard.remove(id);
                return save();
            }
            else {
                return false;
            }
        }
    }
}