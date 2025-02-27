package com.ufund.api.ufundapi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ufund.api.ufundapi.model.Need;

@Tag("Controller-tier")
public class CupboardControllerTest {
    @Test
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deteteNeed(@PathVariable int id) {
        //Setup
        int NeedID = 19;
        String name = "Wood";
        int quantity = 5;
        double cost = 10.0;
        String type = "Physical";
        String deleteString = String.format(Need.STRING_FORMAT,NeedID, name,cost,quantity,type);
        Need need = new Need(NeedID, name, cost, quantity, type);

        return null;
    }
}
