package com.ufund.api.ufundapi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.ufund.api.ufundapi.controller.CupboardController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

@Tag("Controller-tier")
public class CupboardControllerTest {
    private CupboardDAO mockCupboardDAO;
    private CupboardController cupboardController;

    @BeforeEach
    public void setupCupboardController(){
        mockCupboardDAO = mock(CupboardDAO.class);
        cupboardController = new CupboardController(mockCupboardDAO);
    }

    /**
     * @author Giulia Spier
     */
	@Test
	void testGetNeed() throws IOException {
		//setup
		Need need = new Need(12, "Sugar", 10.00, 100, "Food");
		// When the same id is passed in, our mock Cupboard DAO will return the Need object
        when(mockCupboardDAO.getNeed(need.getId())).thenReturn(need);
		//invoke
		ResponseEntity<Need> response = cupboardController.getNeed(need.getId());
		//analyze
		assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
	}


    /**
     * @author Giulia Spier
     */
    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Cupboard DAO will return null, simulating
        // no hero found
        when(mockCupboardDAO.getNeed(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    /**
     * @author Giulia Spier
     */
    @Test
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When getHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCupboardDAO).getNeed(needId);

        // Invoke
        ResponseEntity<Need> response = cupboardController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * @author Giulia Spier
     */
	@Test
    public void testGetNeeds() throws IOException { // getNeeds may throw IOException
        // Setup
        Need[] needs = new Need[2];
        needs[0] = new Need(12, "Sugar", 10.00, 100, "Food");
        needs[1] = new Need(13, "Bread", 15.00, 100, "Food");
        // When getNeeds is called return the needs created above
        when(mockCupboardDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    /**
     * @author Giulia Spier
     */
	@Test
    public void testGetNeedsHandleException() throws IOException { // getNeeds may throw IOException
        // Setup
        // When getNeeds is called on the Mock Cupboard DAO, throw an IOException
        doThrow(new IOException()).when(mockCupboardDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*
     * @author Akiri Jones
     */
    @Test
    public void deleteNeedTest() throws IOException{
        // Setup
        int id = 99;
        when(mockCupboardDAO.deleteNeed(id)).thenReturn(true);
        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(id);
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());   
    }

    /*
     * @author Akiri Jones
     */
    @Test
    public void DeleteNeedNotFound() throws IOException {
        // Setup
        int NeedID = 900;
        when(mockCupboardDAO.deleteNeed(NeedID)).thenReturn(false);
        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(NeedID);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * @author Akiri Jones
     */
    @Test
    public void DeleteNeedInternalError() throws IOException {
        // Setup
        int NeedID = 567;
        doThrow(new IOException()).when(mockCupboardDAO).deleteNeed(NeedID);
        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(NeedID);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateNeed() throws IOException {
        // setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        when(mockCupboardDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = cupboardController.updateNeed(12,need);
        need.setName("Conditioner");

        // invoke
        response = cupboardController.updateNeed(12, need);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testUpdateHeroFailed() throws IOException { 
        // Setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        when(mockCupboardDAO.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.updateNeed(12,need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws IOException { 
        // Setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        doThrow(new IOException()).when(mockCupboardDAO).updateNeed(need);

        // Invoke
        ResponseEntity<Need> response = cupboardController.updateNeed(12, need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateNeed() throws IOException {
        // setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        when(mockCupboardDAO.createNeed(need)).thenReturn(need);

        // invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testCreateNeedFailed() throws IOException {
        // Setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        when(mockCupboardDAO.createNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateNeedHandleException() throws IOException {  
        // Setup
        Need need = new Need(12, "Shampoo", 3.0, 5, "Hygeine");

        doThrow(new IOException()).when(mockCupboardDAO).createNeed(need);

        // Invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testSearchNeed() throws IOException {
        // setup
        String searchTerm = "sh";
        Need[] needs = new Need[2];
        needs[0] = new Need(12, "Shampoo", 3.0, 3, "Hygeine");
        needs[0] = new Need(12, "Shark", 3.0, 3, "Creature");

        when(mockCupboardDAO.findNeeds(searchTerm)).thenReturn(needs);

        // invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchTerm);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException {
        // Setup
        String searchString = "am";
        
        doThrow(new IOException()).when(mockCupboardDAO).findNeeds(searchString);

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
