package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

import java.util.logging.Level;

/**
 * Handles the REST API requests for the Need resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Akiri Jones, Jack Faro, Caden Esterman, Sebastian Canakis Diaz, Giulia Spier
 */
@RestController
@RequestMapping("needs")
public class CupboardController {
    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private CupboardDAO cupboardDAO;


    /**
     * Constructor for the Controller
     * 
     * @param cupboardDAO Provides the Controller a DAO interface to work with.
     * 
     */

    public CupboardController(CupboardDAO cupboardDAO) {
        this.cupboardDAO = cupboardDAO;
    }


    /**
     * Get Needs Endpoint
     * 
     * Returns a Need Object based on the name parameter
     * 
     * @param name 
     * @return Response Entity with {@link Need} Object and HTTP Status OK if it returns the Need succesfully <br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @GetMapping("/{name}")
    public ResponseEntity<Need[]> getNeed(@PathVariable String name) {
        LOG.info("GET /needs/" + name);
        try {
            Need[] need = cupboardDAO.findNeeds(name);
            if (need != null)
                return new ResponseEntity<Need[]>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * Returns all the Needs in the JSON data file.
     * 
     * @return Response Entity with array of  {@link Need} Objects and HTTP Status OK if it returns the Needs succesfully <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {

        LOG.info("GET /needs");
        try {
            Need[] needs = cupboardDAO.getNeeds();
            return new ResponseEntity<Need[]>(needs,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Endpoint to Add a new Need
     * 
     * @param need
     * @return Response Entity with {@link Need} Object and HTTP Status OK if it creates the Need Successfully<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        LOG.info("POST /needs " + need);
        
        try {

            Need needReturned = cupboardDAO.createNeed(need);
            if (needReturned != null) {
                return new ResponseEntity<Need>(needReturned,HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
            
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Endpoint to update a Need
     * 
     * @param need
     * @return Response Entity with {@link Need} Object and HTTP Status OK if it updates the Need Successfully<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /needs/ " + need);
        try {
            Need theNeed = cupboardDAO.updateNeed(need);

            if(theNeed != null) {
                return new ResponseEntity<Need>(theNeed, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Endpoint to delete a Need
     * 
     * @param name - Name of Need being deleted
     * @return Response Entity HTTP Status OK if it deletes the Need Successfully<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /needs/" + id);

        try {
            boolean result = cupboardDAO.deleteNeed(id);
            if(result != false) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }

    }


}