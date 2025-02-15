package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RequestMapping
@RestController
public class CupboardController {
    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private CupboardDAO cupboardDAO;


    public CupboardController(CupboardDAO cupboardDAO) {
        this.cupboardDAO = cupboardDAO;
    }



    @GetMapping("/{name}")
    public ResponseEntity<Need> getNeed(@PathVariable String name) {
         LOG.info("GET /need/" + name);
        try {
            Need need = cupboardDAO.getNeed(name);
            if (need != null)
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {

        LOG.info("GET /needs/");
        try {
            Need[] needs = cupboardDAO.getNeeds();
            return new ResponseEntity<Need[]>(needs,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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


    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /needs " + need);
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



    


}