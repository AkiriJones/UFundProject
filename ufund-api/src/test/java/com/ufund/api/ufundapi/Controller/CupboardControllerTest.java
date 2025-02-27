package com.ufund.api.ufundapi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.ufund.api.ufundapi.controller.CupboardController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

@Tag("Controller-tier")
public class CupboardControllerTest {
    @Mock
    private CupboardDAO cupboardDAO;

    @InjectMocks
    private CupboardController cupboardController;

    public CupboardControllerTest(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * @author Giulia Spier
     */
	@Test
	void testGetNeed() throws IOException
	{
		//setup
		Need need = new Need(12, "Sugar", 10.00, 100, "Food");
		// When the same id is passed in, our mock Cupboard DAO will return the Need object
        when(cupboardDAO.getNeed(need.getId())).thenReturn(need);
		//invoke
		ResponseEntity<Need> response = cupboardController.getNeed(need.getId());
		//analyze
		assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
	}

    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Cupboard DAO will return null, simulating
        // no hero found
        when(cupboardDAO.getNeed(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    
    @Test
    public void deleteNeedTest() {
        //Setup
        // Need[] needs = cupboardDAO.getNeeds();
        int NeedID = 19;
        String name = "Wood";
        int quantity = 5;
        double cost = 10.0;
        String type = "Physical";
        Need need = new Need(NeedID, name, cost, quantity, type);

        //Invoke goes here
        ResponseEntity<Need> expected = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<Need> actual = cupboardController.deleteNeed(NeedID);
 
        assertNotNull(need);
        assertEquals(expected, actual);
        
    }

    @Test
    public void DeleteNeedNotFound(){
        int NeedID = 900;
        ResponseEntity<Need> expected = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Need> actual = cupboardController.deleteNeed(NeedID);

        assertEquals(expected, actual);
    }

    @Test
    public void DeleteNeedInternalError(){
        int NeedID = 567;
        ResponseEntity<Need> expected = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<Need> actual = doThrow(new RuntimeException("Database error")).when(cupboardController.deleteNeed(NeedID));

        assertEquals(expected, actual);
    }
}
