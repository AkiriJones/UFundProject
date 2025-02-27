package com.ufund.api.ufundapi.Persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardFileDAO;

@Tag("Persistence-tier")
public class CupboardFileDAOTest {
    public CupboardFileDAO cupboardFileDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @author Caden Esterman
     * @throws IOException
     */
    @BeforeEach
    public void setupNeedFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testNeeds = new Need[3];
        testNeeds[0] = new Need(1,"Sugar",5.0,9,"Food");
        testNeeds[1] = new Need(2,"Bread",7.0,8,"Food");
        testNeeds[2] = new Need(3,"Shoes",12.0,3,"Clothing");

        when(mockObjectMapper
                .readValue(new File("irrelevant.txt"), Need[].class))
                    .thenReturn(testNeeds);
        
        cupboardFileDAO = new CupboardFileDAO("irrelevant.txt", mockObjectMapper);
    }
    
    /**
     * @author Jack Faro
     * @throws IOException
     */
    @Test
    public void testCreateNeed() throws IOException {
        Need newNeed = new Need(1, "Shirt", 10.0, 5, "Clothing");
        doNothing().when(mockObjectMapper).writeValue(any(File.class), any(Need[].class));

        Need testNeed = cupboardFileDAO.createNeed(newNeed);
        
        assertNotNull(testNeed);
        assertEquals(newNeed.getId(), testNeed.getId());
        assertEquals(newNeed.getName(), testNeed.getName());
        assertEquals(newNeed.getCost(), testNeed.getCost());
        assertEquals(newNeed.getType(), testNeed.getType());
    }

    /**
     * @author Caden Esterman
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class),any(Need[].class));

        Need need = new Need(4,"Shampoo",10.0,8,"Hygeine");

        assertThrows(IOException.class, () -> cupboardFileDAO.createNeed(need),"IOException not thrown");

    }

    /**
     * @author Caden Esterman
     * @throws IOException
     */
    @Test
    public void testGetNeedNotFound() throws IOException {
        // Invoke
        Need need = cupboardFileDAO.getNeed(7);

        // Analyze
        assertEquals(need, null);
    }

    /**
     * @author Caden Esterman
     * @throws IOException
     */
    @Test void testDeleteNeedNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cupboardFileDAO.deleteNeed(7), "Unexpected exception thrown");

        // Analyze
        assertEquals(result, false);
        assertEquals(cupboardFileDAO.cupboard.size(), testNeeds.length);
    }
}
