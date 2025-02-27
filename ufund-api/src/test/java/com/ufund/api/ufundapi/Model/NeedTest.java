package com.ufund.api.ufundapi.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;

@Tag("Model-tier")
public class NeedTest {

    /**
     * @author Caden Esterman
     */
    @Test
    public void testToString() {
        // Setup
        int id = 12;
        String name = "Clothing";
        int quantity = 4;
        double cost = 5.0;
        String type = "Physical";
        String expected_string = String.format(Need.STRING_FORMAT,id, name,cost,quantity,type);
        Need need = new Need(id, name, cost, quantity, type);

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

    /**
     * @author Caden Esterman
     */
    @Test
    public void testEqualsTrue() {
        // Setup
        int id1 = 12;
        String name1 = "Clothing";
        int quantity1 = 4;
        double cost1 = 5.0;
        String type1 = "Physical";
        Need need1 = new Need(id1, name1, cost1, quantity1, type1);

        int id2 = 12;
        String name2 = "Clothing";
        int quantity2 = 4;
        double cost2 = 5.0;
        String type2 = "Physical";
        Need need2 = new Need(id2, name2, cost2, quantity2, type2);
        boolean expected = true;
        // Invoke
        boolean actual = need1.equals(need2);

        // Analyze
        assertEquals(expected, actual);
    }

    /**
     * @author Caden Esterman
     */
    @Test
    public void testEqualsFalse() {
        // Setup
        int id1 = 12;
        String name1 = "Food";
        int quantity1 = 4;
        double cost1 = 5.0;
        String type1 = "Physical";
        Need need1 = new Need(id1, name1, cost1, quantity1, type1);

        int id2 = 12;
        String name2 = "Clothing";
        int quantity2 = 4;
        double cost2 = 5.0;
        String type2 = "Physical";
        Need need2 = new Need(id2, name2, cost2, quantity2, type2);
        boolean expected = false;
        // Invoke
        boolean actual = need1.equals(need2);

        // Analyze
        assertEquals(expected, actual);
    }

    /**
     * @author Giulia Spier
     */
	@Test
	void testNeedGetNameSugar()
	{
		//setup
		Need sugar = new Need(12, "Sugar", 10.00, 100, "Food");
		String expected = "Sugar";
		//invoke
		String actual = sugar.getName();
		//analyze
		assertEquals(expected, actual);
	}

	/**
     * @author Giulia Spier
     */
    @Test
	void testNeedGetCost10()
	{
		//setup
		Need sugar = new Need(12, "Sugar", 10.00, 100, "Food");
		Double expected = 10.00;
		//invoke
		Double actual = sugar.getCost();
		//analyze
		assertEquals(expected, actual);
	}

	/**
     * @author Giulia Spier
     */
    @Test
	void testNeedGetQuantity100()
	{
		//setup
		Need sugar = new Need(12, "Sugar", 10.00, 100, "Food");
		int expected = 100;
		//invoke
		int actual = sugar.getQuantity();
		//analyze
		assertEquals(expected, actual);
	}

	/**
     * @author Giulia Spier
     */
    @Test
	void testNeedGetTypeFood()
	{
		//setup
		Need sugar = new Need(12, "Sugar", 10.00, 100, "Food");
		String expected = "Food";
		//invoke
		String actual = sugar.getType();
		//analyze
		assertEquals(expected, actual);
	}

}
