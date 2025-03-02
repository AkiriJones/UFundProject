package com.ufund.api.ufundapi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.mockito.MockitoAnnotations;
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
}
